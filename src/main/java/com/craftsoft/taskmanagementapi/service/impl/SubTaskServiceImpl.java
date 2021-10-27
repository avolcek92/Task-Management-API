package com.craftsoft.taskmanagementapi.service.impl;

import com.craftsoft.taskmanagementapi.dao.SubTaskRepository;
import com.craftsoft.taskmanagementapi.service.SubTaskService;
import com.craftsoft.taskmanagementapi.domain.SubTask;
import com.craftsoft.taskmanagementapi.dto.SubTaskRequestDTO;
import com.craftsoft.taskmanagementapi.dto.SubTaskResponseDTO;
import com.craftsoft.taskmanagementapi.mapper.SubTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubTaskServiceImpl implements SubTaskService {

    @Autowired
    SubTaskRepository subTaskRepository;

    @Autowired
    SubTaskMapper subTaskMapper;

    /**
     * Creating a new SubTask.
     *
     * @param subTaskRequestDTO - new SubTask data transfer object.
     * @return created SubTask id.
     */
    @Override
    public int createSubTask(SubTaskRequestDTO subTaskRequestDTO) {
        return subTaskRepository.save(subTaskMapper.toDomain(subTaskRequestDTO)).getId();
    }

    /**
     * Retrieving a SubTask by id.
     *
     * @param subTaskId - SubTask id, which we want to retrieve.
     * @return retrieved SubTask data transfer object.
     */
    @Override
    public SubTaskResponseDTO getSubTaskById(int subTaskId) {
        return subTaskRepository.findById(subTaskId).map(task -> subTaskMapper.toDTO(task)).orElse(null);
    }

    /**
     * Updating a SubTask.
     * Check or SubTask which we want to update exist.
     *
     * @param subTaskRequestDTO - updatable SubTask data transfer object.
     * @return updated SubTask id.
     */
    @Override
    public Integer updateSubTask(SubTaskRequestDTO subTaskRequestDTO) {
        Optional<SubTask> oldTask = subTaskRepository.findById(subTaskRequestDTO.getId());
        if (oldTask.isPresent()) {
            return subTaskRepository.save(subTaskMapper.toDomain(subTaskRequestDTO)).getId();
        } else {
            return null;
        }
    }

    /**
     * Deleting a SubTask.
     *
     * @param subTaskId - id of SubTask, which we want to delete.
     * @return deleted SubTask id.
     */
    @Override
    public Integer deleteSubTask(int subTaskId) {
        try {
            subTaskRepository.deleteById(subTaskId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return subTaskId;
    }
}
