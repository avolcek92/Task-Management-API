package craftsoft.taskmanagementapi.service;

import craftsoft.taskmanagementapi.dto.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    Page<TaskDTO> getAllTasks(int page, int size);

    TaskDTO updateTask(TaskDTO taskDTO);

    void deleteTask(long id);
}
