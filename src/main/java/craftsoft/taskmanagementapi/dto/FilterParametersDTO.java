package craftsoft.taskmanagementapi.dto;

import craftsoft.taskmanagementapi.domain.enums.Group;
import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FilterParametersDTO {

    private String name;

    private String description;

    private Group group;

    private Status status;

    private String assignee;

    private Long duration;

    public boolean isEmpty() {
        return (name == null && description == null && group == null && status == null && assignee == null && duration == null);
    }
}
