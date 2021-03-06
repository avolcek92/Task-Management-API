package com.craftsoft.taskmanagementapi.dto;

import com.craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class SubTaskResponseDTO {

    private int id;

    private String name;

    private String description;

    private Status status;
}
