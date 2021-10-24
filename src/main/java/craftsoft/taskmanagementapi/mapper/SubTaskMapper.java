package craftsoft.taskmanagementapi.mapper;

import craftsoft.taskmanagementapi.domain.SubTask;
import craftsoft.taskmanagementapi.dto.SubTaskRequestDTO;
import craftsoft.taskmanagementapi.dto.SubTaskResponseDTO;
import org.springframework.stereotype.Component;

@Component("subtaskMapper")
public class SubTaskMapper {

    public SubTaskResponseDTO toDTO(SubTask subTaskDomain) {
        return SubTaskResponseDTO.builder()
                .id(subTaskDomain.getId())
                .name(subTaskDomain.getName())
                .description(subTaskDomain.getDescription())
                .status(subTaskDomain.getStatus())
                .build();
    }

    public SubTask toDomain(SubTaskResponseDTO subTaskResponseDTO) {
        return SubTask.builder()
                .name(subTaskResponseDTO.getName())
                .description(subTaskResponseDTO.getDescription())
                .status(subTaskResponseDTO.getStatus())
                .build();
    }

    public SubTask toDomain(SubTaskRequestDTO subTaskRequestDTO) {
        return SubTask.builder()
                .name(subTaskRequestDTO.getName())
                .description(subTaskRequestDTO.getDescription())
                .status(subTaskRequestDTO.getStatus())
                .build();
    }
}
