package craftsoft.taskmanagementapi.controller;

import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import craftsoft.taskmanagementapi.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/tasks", produces = APPLICATION_JSON_VALUE)
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTask(@RequestBody TaskResponseDTO taskResponseDTO) {
        int createdTaskId = taskService.createTask(taskResponseDTO);
        logger.info("New task was created id:{}", createdTaskId);
        return ResponseEntity.created(URI.create("/tasks/" + createdTaskId)).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") int id) {
        Optional<TaskResponseDTO> task = taskService.getTaskById(id);
        if (task.isEmpty()) {
            logger.info("Task with id:{} not exist", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Task was retrieved id:{}", id);
        return ResponseEntity.ok().body(task.get());
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "size", defaultValue = "20") int size,
            @RequestParam(required = false, name = "sortField", defaultValue = "name") String sortField,
            @RequestParam(required = false, name = "direction", defaultValue = "DESC") String direction,
            @RequestParam(required = false, name = "search") String search) {
        Page<TaskResponseDTO> taskResponseDTOPage = taskService.getAllTasks(page, size, sortField, direction, search);
        if (taskResponseDTOPage.isEmpty()) {
            logger.info("Empty list of Tasks was retrieved");
            return ResponseEntity.noContent().build();
        }
        logger.info("List of Tasks was retrieved, size:{}", taskResponseDTOPage.getSize());
        return ResponseEntity.ok().body(taskResponseDTOPage);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTask(@RequestBody TaskResponseDTO taskResponseDTO) { // no content/if exist
        taskService.updateTask(taskResponseDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") int id) { // no content/ if exist
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
