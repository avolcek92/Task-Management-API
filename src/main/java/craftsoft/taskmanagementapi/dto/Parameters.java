package craftsoft.taskmanagementapi.dto;

import craftsoft.taskmanagementapi.domain.enums.Group;
import craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Builder
@Getter
@Setter
public class Parameters {
    private int page;

    private int pageSize;

    private String sortField;

    private String name;

    private String description;

    private Group group;

    private Status status;

    private String assignee;

    private Long duration;
}
