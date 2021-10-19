package craftsoft.taskmanagementapi.service;

import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TaskService {

    int createTask(TaskResponseDTO taskResponseDTO);

    Optional<TaskResponseDTO> getTaskById(int id);

    Page<TaskResponseDTO> getAllTasks(int page, int size, String sortField, String direction, String search);

    TaskResponseDTO updateTask(TaskResponseDTO taskResponseDTO);

    void deleteTask(int id);
}
