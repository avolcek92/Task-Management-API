package craftsoft.taskmanagementapi.service.impl;

import craftsoft.taskmanagementapi.dao.TaskRepository;
import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.domain.enums.Status;
import craftsoft.taskmanagementapi.dto.FilterParametersDTO;
import craftsoft.taskmanagementapi.dto.TaskRequestDTO;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import craftsoft.taskmanagementapi.mapper.TaskMapper;
import craftsoft.taskmanagementapi.service.TaskService;
import craftsoft.taskmanagementapi.service.filter.GroupFilterSpecification;
import craftsoft.taskmanagementapi.service.filter.LongFilterSpecification;
import craftsoft.taskmanagementapi.service.filter.StatusFilterSpecification;
import craftsoft.taskmanagementapi.service.filter.StringFilterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskMapper taskMapper;

    /**
     * Creating a new Task.
     *
     * @param taskRequestDTO - new Task data transfer object.
     * @return created Task id.
     */
    @Override
    public int createTask(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        taskMapper.toDomain(task, taskRequestDTO);
        task = taskRepository.save(task);
        return task.getId();
    }

    /**
     * Retrieving a Task by id.
     *
     * @param taskId - Task id, which we want to retrieve.
     * @return retrieved task data transfer object.
     */
    @Override
    public TaskResponseDTO getTaskById(int taskId) {
        return taskRepository.findById(taskId).map(task -> taskMapper.toDTO(task)).orElse(null);
    }

    /**
     * Retrieving all possible Tasks by parameters.
     *
     * @param filterParametersDTO - filtering parameters data transfer object with columns values.
     * @param page                - page number.
     * @param pageSize            - size of pages.
     * @param sortField           - field according to which list will be sorted (ascending).
     * @return list of Task by parameters.
     */
    @Override
    public Page<TaskResponseDTO> getAllTasks(FilterParametersDTO filterParametersDTO, int page, int pageSize, String sortField) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortField).ascending());
        if (filterParametersDTO.isEmpty()) {
            return taskRepository.findAll(pageable).map(task -> taskMapper.toDTO(task));
        } else {

            return getAllTaskByFilters(filterParametersDTO, pageable);
        }
    }

    /**
     * Creating every column filter specification and
     * retrieve all possible Tasks by column filters.
     *
     * @param filterParametersDTO - filtering parameters data transfer object with columns values.
     * @param pageable            - pageable parameters.
     * @return list of Task by filtering parameters.
     */
    private Page<TaskResponseDTO> getAllTaskByFilters(FilterParametersDTO filterParametersDTO, Pageable pageable) {
        StringFilterSpecification nameColumnSpecification = new StringFilterSpecification("name", filterParametersDTO.getName());
        StringFilterSpecification descriptionColumnSpecification = new StringFilterSpecification("description", filterParametersDTO.getDescription());
        StatusFilterSpecification statusColumnSpecification = new StatusFilterSpecification("status", filterParametersDTO.getStatus());
        GroupFilterSpecification groupColumnSpecification = new GroupFilterSpecification("group", filterParametersDTO.getGroup());
        StringFilterSpecification assigneeColumnSpecification = new StringFilterSpecification("assignee", filterParametersDTO.getAssignee());
        LongFilterSpecification durationColumnSpecification = new LongFilterSpecification("duration", filterParametersDTO.getDuration());
        List<TaskResponseDTO> filteredListDTOS = taskRepository.findAll(Specification
                        .where(nameColumnSpecification)
                        .or(descriptionColumnSpecification)
                        .or(statusColumnSpecification)
                        .or(groupColumnSpecification)
                        .or(assigneeColumnSpecification)
                        .or(durationColumnSpecification))
                .stream()
                .map(task -> taskMapper.toDTO(task))
                .collect(Collectors.toList());
        return new PageImpl<>(filteredListDTOS, pageable, filteredListDTOS.size());
    }

    /**
     * Updating a Task.
     * Check or Task which we want to update exist.
     * Call handleTaskProgressDates and  setTaskDurationInHours methods.
     *
     * @param taskRequestDTO - updatable Task data transfer object.
     * @return updated Task id.
     */
    @Override
    public Integer updateTask(TaskRequestDTO taskRequestDTO) {
        Optional<Task> oldTask = taskRepository.findById(taskRequestDTO.getId());
        if (oldTask.isPresent()) {
            Task task = oldTask.get();
            handleTaskProgressDates(taskRequestDTO, task);
            taskMapper.toDomain(task, taskRequestDTO);
            setTaskDurationInHours(task);
            taskRepository.save(task);
            return task.getId();
        } else {
            return null;
        }
    }

    /**
     * Setting Task duration by status.
     * Duration start calculating only when status changed to IN_PROGRESS.
     *
     * @param task - Task for which duration is setting.
     */
    private void setTaskDurationInHours(Task task) {
        if (task.getStatus().equals(Status.IN_PROGRESS) || task.getStatus().equals(Status.IN_TEST)) {
            long hours = ChronoUnit.HOURS.between(task.getInProgressAt(), LocalDateTime.now());
            task.setDuration(hours);
        } else if (task.getStatus().equals(Status.DONE)) {
            long hours = ChronoUnit.HOURS.between(task.getInProgressAt(), task.getDoneAt());
            task.setDuration(hours);
        }
    }

    /**
     * Setting dates of Task statuses.
     *
     * @param newTask - Task data transfer object with new data.
     * @param oldTask - Task with old data.
     */
    private void handleTaskProgressDates(TaskRequestDTO newTask, Task oldTask) {
        if (oldTask.getStatus().equals(Status.CREATED) && newTask.getStatus().equals(Status.IN_PROGRESS)) {
            oldTask.setInProgressAt(LocalDateTime.now());
        } else if (oldTask.getStatus().equals(Status.IN_PROGRESS) && newTask.getStatus().equals(Status.IN_TEST)) {
            oldTask.setInTestAt(LocalDateTime.now());
        } else if (oldTask.getStatus().equals(Status.IN_PROGRESS) || oldTask.getStatus().equals(Status.IN_TEST) && newTask.getStatus().equals(Status.DONE)) {
            oldTask.setDoneAt(LocalDateTime.now());
        }
    }

    /**
     * Deleting a Task.
     *
     * @param taskId - id of Task, which we want to delete.
     * @return deleted Task id.
     */
    @Override
    public Integer deleteTask(int taskId) {
        try {
            taskRepository.deleteById(taskId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return taskId;
    }
}
