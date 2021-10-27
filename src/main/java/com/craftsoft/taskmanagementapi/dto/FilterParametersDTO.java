package com.craftsoft.taskmanagementapi.dto;

import com.craftsoft.taskmanagementapi.domain.enums.Group;
import com.craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
