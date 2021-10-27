package com.craftsoft.taskmanagementapi.service.unit;

import com.craftsoft.taskmanagementapi.dao.SubTaskRepository;
import com.craftsoft.taskmanagementapi.domain.SubTask;
import com.craftsoft.taskmanagementapi.domain.Task;
import com.craftsoft.taskmanagementapi.domain.enums.Status;
import com.craftsoft.taskmanagementapi.dto.SubTaskRequestDTO;
import com.craftsoft.taskmanagementapi.dto.SubTaskResponseDTO;
import com.craftsoft.taskmanagementapi.mapper.SubTaskMapper;
import com.craftsoft.taskmanagementapi.service.impl.SubTaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SubTaskServiceUnitTests {

    @Mock
    private SubTaskRepository subTaskRepository;

    @Mock
    private SubTaskMapper subTaskMapper;

    @InjectMocks
    private SubTaskServiceImpl subTaskService;

    @Test
    void test_getSubTaskById() {

        Task task = new Task();
        Optional<SubTask> subTask = Optional.of(new SubTask(1, "subTask", "description", Status.DONE, task));
        Optional<SubTask> emptyTask = Optional.empty();
        SubTaskResponseDTO subTaskResponseDTO = new SubTaskResponseDTO(1, "Subtask", "description", Status.DONE);

        when(subTaskRepository.findById(1)).thenReturn(subTask);
        when(subTaskRepository.findById(2)).thenReturn(emptyTask);
        when(subTaskMapper.toDTO(subTask.get())).thenReturn(subTaskResponseDTO);

        SubTaskResponseDTO subTaskResponseDTOSuccessful = subTaskService.getSubTaskById(1);
        SubTaskResponseDTO subTaskResponseDTOWithNull = subTaskService.getSubTaskById(2);

        assertEquals(subTaskResponseDTO, subTaskResponseDTOSuccessful);
        assertNull(subTaskResponseDTOWithNull);
    }

    @Test
    void test_createSubTask() {

        SubTask subTask = new SubTask();
        subTask.setId(1);
        SubTaskRequestDTO subTaskRequestDTO = new SubTaskRequestDTO();

        when(subTaskMapper.toDomain(subTaskRequestDTO)).thenReturn(subTask);
        when(subTaskRepository.save(subTask)).thenReturn(subTask);

        int savedSubTaskId = subTaskService.createSubTask(subTaskRequestDTO);

        assertEquals(1, savedSubTaskId);
    }


    @Test
    void test_updateSubTask() {

        Task task = new Task();
        Optional<SubTask> subTask = Optional.of(new SubTask(1, "Subtask", "description", Status.DONE, task));
        Optional<SubTask> emptyTask = Optional.empty();
        SubTaskRequestDTO subTaskRequestDTO = new SubTaskRequestDTO(1, "Subtask", "description", Status.DONE, new Task());
        SubTaskRequestDTO wrongSubTaskRequestDTO = new SubTaskRequestDTO(2, "Subtask", "description", Status.DONE, new Task());

        when(subTaskRepository.findById(1)).thenReturn(subTask);
        when(subTaskRepository.findById(2)).thenReturn(emptyTask);
        when(subTaskRepository.save(subTask.get())).thenReturn(subTask.get());
        when(subTaskMapper.toDomain(subTaskRequestDTO)).thenReturn(subTask.get());

        Integer subTaskIdSuccessful = subTaskService.updateSubTask(subTaskRequestDTO);
        Integer subTaskIdNull = subTaskService.updateSubTask(wrongSubTaskRequestDTO);

        assertEquals(1, subTaskIdSuccessful);
        assertNull(subTaskIdNull);
    }

    @Test
    void test_deleteSubTask() {

        doNothing().when(subTaskRepository).deleteById(1);
        doThrow(new EmptyResultDataAccessException(1)).when(subTaskRepository).deleteById(2);

        Integer subTaskId = subTaskService.deleteSubTask(1);
        Integer subTaskIdNull = subTaskService.deleteSubTask(2);

        assertEquals(1, subTaskId);
        assertNull(subTaskIdNull);
    }
}
