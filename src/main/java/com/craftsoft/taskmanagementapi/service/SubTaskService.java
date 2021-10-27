package com.craftsoft.taskmanagementapi.service;

import com.craftsoft.taskmanagementapi.dto.SubTaskRequestDTO;
import com.craftsoft.taskmanagementapi.dto.SubTaskResponseDTO;

public interface SubTaskService {

    int createSubTask(SubTaskRequestDTO subTaskRequestDTO);

    SubTaskResponseDTO getSubTaskById(int subTaskId);

    Integer updateSubTask(SubTaskRequestDTO subTaskRequestDTO);

    Integer deleteSubTask(int subTaskId);
}
