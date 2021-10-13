package craftsoft.taskmanagementapi.controller;

import craftsoft.taskmanagementapi.domain.Task;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/v1/task")
public class TaskController {

    @PostMapping
    public String createTask(@RequestBody Task task) {
        return "task created";
    }
}
