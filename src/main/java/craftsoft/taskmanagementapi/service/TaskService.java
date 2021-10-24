package craftsoft.taskmanagementapi.service;

import craftsoft.taskmanagementapi.dto.Parameters;
import craftsoft.taskmanagementapi.dto.TaskRequestDTO;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

    int createTask(TaskRequestDTO taskRequestDTO);

    TaskResponseDTO getTaskById(int id);

    Page<TaskResponseDTO> getAllTasks(Parameters parameters);

    Integer updateTask(TaskRequestDTO taskRequestDTO);

    Integer deleteTask(int id);
}
