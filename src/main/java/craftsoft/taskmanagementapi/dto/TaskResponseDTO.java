package craftsoft.taskmanagementapi.dto;

import craftsoft.taskmanagementapi.domain.SubTask;
import craftsoft.taskmanagementapi.domain.enums.Group;
import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class TaskResponseDTO {

    private int id;

    private String name;

    private String description;

    private Group group;

    private Status status;

    private String assignee;

    private long duration;

    private List<SubTaskDTO> subTask;
}
