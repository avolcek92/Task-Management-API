package com.craftsoft.taskmanagementapi.dto;

import com.craftsoft.taskmanagementapi.domain.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.craftsoft.taskmanagementapi.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubTaskRequestDTO {

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
    private Status status;

    @JsonProperty(required = true)
    private Task task;
}
