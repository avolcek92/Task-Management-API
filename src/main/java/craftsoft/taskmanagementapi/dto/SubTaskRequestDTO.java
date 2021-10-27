package craftsoft.taskmanagementapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubTaskRequestDTO {

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

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private Status status;
}
