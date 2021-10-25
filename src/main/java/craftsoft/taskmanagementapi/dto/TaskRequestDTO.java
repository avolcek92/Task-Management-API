package craftsoft.taskmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import craftsoft.taskmanagementapi.domain.enums.Group;
import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
public class TaskRequestDTO {

    private int id;

    @JsonProperty(required = true)
    @NotEmpty
    @Size(min = 1, max = 30)
    private String name;

    @JsonProperty(required = true)
    @NotEmpty
    @Size(min = 1, max = 300)
    private String description;

    @JsonProperty(required = true)
    private Group group;

    @JsonProperty(required = true)
    private Status status;

    @Size(min = 1, max = 50)
    private String assignee;

    private List<SubTaskResponseDTO> subTask;
}
