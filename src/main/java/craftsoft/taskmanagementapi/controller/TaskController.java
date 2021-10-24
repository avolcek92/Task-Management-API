package craftsoft.taskmanagementapi.controller;

import craftsoft.taskmanagementapi.domain.enums.Group;
import craftsoft.taskmanagementapi.domain.enums.Status;
import craftsoft.taskmanagementapi.dto.Parameters;
import craftsoft.taskmanagementapi.dto.TaskRequestDTO;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import craftsoft.taskmanagementapi.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/tasks", produces = APPLICATION_JSON_VALUE)
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        int createdTaskId = taskService.createTask(taskRequestDTO);
        logger.info("New task was created id:{}", createdTaskId);
        return ResponseEntity.created(URI.create("/tasks/" + createdTaskId)).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") int id) {
        TaskResponseDTO taskResponseDTO = taskService.getTaskById(id);
        if (taskResponseDTO == null) {
            logger.info("Task with id:{} not exist", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Task was retrieved id:{}", id);
        return ResponseEntity.ok().body(taskResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "20") int pageSize,
            @RequestParam(required = false, name = "sortField", defaultValue = "name") String sortField,
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "group") Group group,
            @RequestParam(required = false, name = "status") Status status,
            @RequestParam(required = false, name = "assignee") String assignee,
            @RequestParam(required = false, name = "duration") Long duration) {
        Parameters parameters = Parameters.builder()
                .page(page)
                .pageSize(pageSize)
                .sortField(sortField)
                .name(name)
                .description(description)
                .group(group)
                .status(status)
                .assignee(assignee)
                .duration(duration)
                .build();
        Page<TaskResponseDTO> taskResponseDTOPage = taskService.getAllTasks(parameters);
        if (taskResponseDTOPage.isEmpty()) {
            logger.info("Empty list of Tasks was retrieved");
            return ResponseEntity.noContent().build();
        }
        logger.info("List of Tasks was retrieved, size:{}", taskResponseDTOPage.getTotalElements());
        return ResponseEntity.ok().body(taskResponseDTOPage);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        Integer taskId = taskService.updateTask(taskRequestDTO);
        if (taskId == null) {
            logger.info("Can't update, Task not exist");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Task successfully updated, id:{}", taskId);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") int id) {
        Integer taskId = taskService.deleteTask(id);
        if (taskId == null) {
            logger.info("Can't delete, Task already not exist");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Task successfully deleted, id:{}", taskId);
            return ResponseEntity.noContent().build();
        }
    }
}
