package craftsoft.taskmanagementapi.mapper;

import craftsoft.taskmanagementapi.domain.SubTask;
import craftsoft.taskmanagementapi.dto.SubTaskDTO;
import org.springframework.stereotype.Component;

@Component("subtaskMapper")
public class SubTaskMapper {

    public SubTaskDTO toDTO(SubTask subTaskDomain) {
        return SubTaskDTO.builder()
                .id(subTaskDomain.getId())
                .name(subTaskDomain.getName())
                .description(subTaskDomain.getDescription())
                .status(subTaskDomain.getStatus())
                .build();
    }

    public SubTask toDomain(SubTaskDTO subTaskDTO) {
        return SubTask.builder()
                .name(subTaskDTO.getName())
                .description(subTaskDTO.getDescription())
                .status(subTaskDTO.getStatus())
                .build();
    }
}
