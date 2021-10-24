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
    @NotBlank
    @Max(value = 30)
    private String name;

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @Max(value = 300)
    private String description;

    //TODO Add pattern validation
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private Group group;

    //TODO Add pattern validation
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private Status status;

    @JsonProperty()
    @Size(min = 2, max = 50)
    private String assignee;

    @JsonProperty()
    private List<SubTaskDTO> subTask;

}
