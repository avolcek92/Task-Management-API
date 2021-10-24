package craftsoft.taskmanagementapi.service;

import craftsoft.taskmanagementapi.dto.FilterParametersDTO;
import craftsoft.taskmanagementapi.dto.TaskRequestDTO;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

    int createTask(TaskRequestDTO taskRequestDTO);

    TaskResponseDTO getTaskById(int taskId);

    Page<TaskResponseDTO> getAllTasks(FilterParametersDTO filterParametersDTO, int page, int pageSize, String sortField);

    Integer updateTask(TaskRequestDTO taskRequestDTO);

    Integer deleteTask(int taskId);
}
