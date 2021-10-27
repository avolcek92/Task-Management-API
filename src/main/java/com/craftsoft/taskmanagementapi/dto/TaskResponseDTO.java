package com.craftsoft.taskmanagementapi.dto;

import com.craftsoft.taskmanagementapi.domain.enums.Group;
import com.craftsoft.taskmanagementapi.domain.enums.Status;
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

    private Long duration;

    private List<SubTaskResponseDTO> subTask;
}
