package craftsoft.taskmanagementapi.dto;

import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class SubTaskDTO {
    private long id;

    private String name;

    private String description;

    private Status status;
}
