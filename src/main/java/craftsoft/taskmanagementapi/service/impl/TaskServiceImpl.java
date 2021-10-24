package craftsoft.taskmanagementapi.service.impl;

import craftsoft.taskmanagementapi.dao.TaskRepository;
import craftsoft.taskmanagementapi.dto.Parameters;
import craftsoft.taskmanagementapi.dto.TaskRequestDTO;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import craftsoft.taskmanagementapi.mapper.TaskMapper;
import craftsoft.taskmanagementapi.service.TaskService;
import craftsoft.taskmanagementapi.service.filter.GroupFilterSpecification;
import craftsoft.taskmanagementapi.service.filter.LongFilterSpecification;
import craftsoft.taskmanagementapi.service.filter.StatusFilterSpecification;
import craftsoft.taskmanagementapi.service.filter.StringFilterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public int createTask(TaskRequestDTO taskRequestDTO) {
        return taskRepository.save(taskMapper.toDomain(taskRequestDTO)).getId();
    }

    @Override
    public TaskResponseDTO getTaskById(int id) {
        return taskRepository.findById(id).map(task -> taskMapper.toDTO(task)).orElse(null);
    }

    @Override
    public Page<TaskResponseDTO> getAllTasks(Parameters parameters) {
        Pageable pageable = PageRequest.of(parameters.getPage(), parameters.getPageSize(), Sort.by(parameters.getSortField()).ascending());
        if (parameters.getName() == null && parameters.getDescription() == null && parameters.getGroup() == null && parameters.getStatus() == null && parameters.getAssignee() == null && parameters.getDuration() == null) {
            return taskRepository.findAll(pageable).map(task -> taskMapper.toDTO(task));
        } else {
            return new PageImpl<>(searchInAllColumnsByValue(parameters), pageable, searchInAllColumnsByValue(parameters).size());
        }

    }

    private List<TaskResponseDTO> searchInAllColumnsByValue(Parameters parameters) {
        StringFilterSpecification nameColumnSpecification = new StringFilterSpecification("name", parameters.getName());
        StringFilterSpecification descriptionColumnSpecification = new StringFilterSpecification("description", parameters.getDescription());
        StatusFilterSpecification statusColumnSpecification = new StatusFilterSpecification("status", parameters.getStatus());
        GroupFilterSpecification groupColumnSpecification = new GroupFilterSpecification("group", parameters.getGroup());
        StringFilterSpecification assigneeColumnSpecification = new StringFilterSpecification("assignee", parameters.getAssignee());
        LongFilterSpecification durationColumnSpecification = new LongFilterSpecification("duration", parameters.getDuration());
        return taskRepository.findAll(Specification
                        .where(nameColumnSpecification)
                        .or(descriptionColumnSpecification)
                        .or(statusColumnSpecification)
                        .or(groupColumnSpecification)
                        .or(assigneeColumnSpecification)
                        .or(durationColumnSpecification))
                .stream()
                .map(task -> taskMapper.toDTO(task))
                .collect(Collectors.toList());
    }

    @Override
    public Integer updateTask(TaskRequestDTO taskRequestDTO) {
        if (taskRepository.findById(taskRequestDTO.getId()).isPresent()) {
            taskRepository.save(taskMapper.toDomain(taskRequestDTO));
            return taskRequestDTO.getId(); //TODO implement date in progres, done itd
        } else {
            return null;
        }
    }

    @Override
    public Integer deleteTask(int id) {
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            return null;
        }
        return id;
    }
}
