package craftsoft.taskmanagementapi.controller;

import craftsoft.taskmanagementapi.dto.TaskDTO;
import craftsoft.taskmanagementapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @GetMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    public Page<TaskDTO> getAllTasks(@RequestParam int page, @RequestParam int size) {
        return taskService.getAllTasks(page, size);
    }

    @PutMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    public TaskDTO updateTask(@RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskDTO);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = "application/json",
            consumes = "application/json"
    )
    public void deleteTask(@PathVariable("id") long id) {
        taskService.deleteTask(id);
    }





}
