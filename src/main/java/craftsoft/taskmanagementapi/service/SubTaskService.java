package craftsoft.taskmanagementapi.service;

import craftsoft.taskmanagementapi.dto.SubTaskRequestDTO;
import craftsoft.taskmanagementapi.dto.SubTaskResponseDTO;

public interface SubTaskService {

    int createSubTask(SubTaskRequestDTO subTaskRequestDTO);

    SubTaskResponseDTO getSubTaskById(int subTaskId);

    Integer updateSubTask(SubTaskRequestDTO subTaskRequestDTO);

    Integer deleteSubTask(int subTaskId);
}
