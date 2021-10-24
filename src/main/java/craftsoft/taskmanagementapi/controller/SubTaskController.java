package craftsoft.taskmanagementapi.controller;

import craftsoft.taskmanagementapi.dto.SubTaskRequestDTO;
import craftsoft.taskmanagementapi.dto.SubTaskResponseDTO;
import craftsoft.taskmanagementapi.service.SubTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/subTasks", produces = APPLICATION_JSON_VALUE)
public class SubTaskController {

    private static final Logger logger = LoggerFactory.getLogger(SubTaskController.class);

    @Autowired
    SubTaskService subTaskService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createSubTask(@RequestBody SubTaskRequestDTO subTaskRequestDTO) {
        int createdSubTaskId = subTaskService.createSubTask(subTaskRequestDTO);
        logger.info("New SubTask was created id:{}", createdSubTaskId);
        return ResponseEntity.created(URI.create("/subTasks/" + createdSubTaskId)).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SubTaskResponseDTO> getSubTaskById(@PathVariable("id") int id) {
        SubTaskResponseDTO subTaskResponseDTO = subTaskService.getSubTaskById(id);
        if (subTaskResponseDTO == null) {
            logger.info("SubTask with id:{} not exist", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("SubTask was retrieved id:{}", id);
        return ResponseEntity.ok().body(subTaskResponseDTO);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateSubTask(@RequestBody SubTaskRequestDTO subTaskRequestDTO) {
        Integer subTaskId = subTaskService.updateSubTask(subTaskRequestDTO);
        if (subTaskId == null) {
            logger.info("Can't update, SubTask not exist");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("SubTask successfully updated, id:{}", subTaskId);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(@PathVariable("subTaskId") int id) {
        Integer subTaskId = subTaskService.deleteSubTask(id);
        if (subTaskId == null) {
            logger.info("Can't delete, SubTask already not exist");
            return ResponseEntity.notFound().build();
        } else {
            logger.info("SubTask successfully deleted, id:{}", subTaskId);
            return ResponseEntity.noContent().build();
        }
    }
}
