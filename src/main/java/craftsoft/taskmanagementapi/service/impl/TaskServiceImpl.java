package craftsoft.taskmanagementapi.service.impl;

import craftsoft.taskmanagementapi.dao.TaskRepository;
import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import craftsoft.taskmanagementapi.mapper.TaskMapper;
import craftsoft.taskmanagementapi.service.TaskService;
import craftsoft.taskmanagementapi.service.filter.FilterSpecification;
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
    public Page<TaskResponseDTO> getAllTasks(int page, int size, String sortField, String direction, String searchValue) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortField).ascending());
        if (searchValue == null) {
            return taskRepository.findAll(pageable).map(task -> taskMapper.toDTO(task));
        } else {
            return new PageImpl<>(searchInAllColumnsByValue(searchValue), pageable, searchInAllColumnsByValue(searchValue).size());
        }
    }

    private List<TaskResponseDTO> searchInAllColumnsByValue(String value) {
        FilterSpecification filterSpecificationForNameColumn = new FilterSpecification("name", value);
        FilterSpecification filterSpecificationForDescriptionColumn = new FilterSpecification("description", value);
        FilterSpecification filterSpecificationForGroupColumn = new FilterSpecification("group", value);
        FilterSpecification filterSpecificationForStatusColumn = new FilterSpecification("status", value);
        FilterSpecification filterSpecificationForAssigneeColumn = new FilterSpecification("assignee", value);
        FilterSpecification filterSpecificationForDurationColumn = new FilterSpecification("duration", value);
        return taskRepository.findAll(Specification
                        .where(filterSpecificationForDescriptionColumn)
                        .or(filterSpecificationForNameColumn)
                        .or(filterSpecificationForGroupColumn)
                        .or(filterSpecificationForStatusColumn)
                        .or(filterSpecificationForAssigneeColumn)
                        .or(filterSpecificationForDurationColumn))
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
