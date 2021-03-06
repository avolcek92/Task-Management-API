package com.craftsoft.taskmanagementapi.controller;

import com.craftsoft.taskmanagementapi.dto.FilterParametersDTO;
import com.craftsoft.taskmanagementapi.dto.TaskRequestDTO;
import com.craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import com.craftsoft.taskmanagementapi.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/tasks", produces = APPLICATION_JSON_VALUE)
@Validated
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    TaskService taskService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        int createdTaskId = taskService.createTask(taskRequestDTO);
        logger.info("New task was created id:{}", createdTaskId);
        return ResponseEntity.created(URI.create("/tasks/" + createdTaskId)).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable("id") @Min(1) int id) {
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
            @RequestParam(required = false, name = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, name = "pageSize", defaultValue = "20") @Min(1) @Max(50) int pageSize,
            @RequestParam(required = false, name = "sortField", defaultValue = "name") @Size(min = 4, max = 11) String sortField,
            @Valid FilterParametersDTO filterParametersDTO) {
        Page<TaskResponseDTO> taskResponseDTOPage = taskService.getAllTasks(filterParametersDTO, page, pageSize, sortField);
        if (taskResponseDTOPage.isEmpty()) {
            logger.info("Empty list of Tasks was retrieved");
            return ResponseEntity.noContent().build();
        }
        logger.info("List of Tasks was retrieved, size:{}", taskResponseDTOPage.getTotalElements());
        return ResponseEntity.ok().body(taskResponseDTOPage);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
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
    public ResponseEntity<Void> deleteTask(@PathVariable("taskId") @Min(1) int id) {
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
