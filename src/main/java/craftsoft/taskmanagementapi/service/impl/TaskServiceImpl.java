package craftsoft.taskmanagementapi.service.impl;

import craftsoft.taskmanagementapi.dao.TaskRepository;
import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.dto.Parameters;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    @Override
    public int createTask(TaskResponseDTO taskResponseDTO) {
        return taskRepository.save(taskMapper.toDomain(taskResponseDTO)).getId();
    }

    @Override
    public Optional<TaskResponseDTO> getTaskById(int id) {
        return taskRepository.getTaskById(id);
    }

    @Override
    public Page<TaskResponseDTO> getAllTasks(Parameters parameters) {
        Pageable pageable = PageRequest.of(parameters.getPage(), parameters.getPageSize(), Sort.by(parameters.getSortField()).ascending());
        if (parameters.getName() == null & parameters.getDescription() == null & parameters.getGroup() == null & parameters.getStatus() == null & parameters.getAssignee() == null & parameters.getDuration() == null) {
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
    public TaskResponseDTO updateTask(TaskResponseDTO taskResponseDTO) {
        Task task = taskMapper.toDomain(taskResponseDTO);
        return taskMapper.toDTO(taskRepository.save(task));
    }

    @Override
    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}
