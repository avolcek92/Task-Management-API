package craftsoft.taskmanagementapi.service;

import craftsoft.taskmanagementapi.dao.TaskRepository;
import craftsoft.taskmanagementapi.domain.Task;
import craftsoft.taskmanagementapi.domain.enums.Group;
import craftsoft.taskmanagementapi.domain.enums.Status;
import craftsoft.taskmanagementapi.dto.FilterParametersDTO;
import craftsoft.taskmanagementapi.dto.TaskRequestDTO;
import craftsoft.taskmanagementapi.dto.TaskResponseDTO;
import craftsoft.taskmanagementapi.mapper.TaskMapper;
import craftsoft.taskmanagementapi.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    void test_getAllFilteredAndPaginatedAndSortedTasks() {
        //Task Repository Mock
        Pageable pageable = PageRequest.of(0, 20, Sort.by("name").ascending());
        FilterParametersDTO filterParametersDTO = new FilterParametersDTO(null, null, Group.BACKEND, null, null, null);
        List<Task> tasks = new ArrayList<>();
        Task firstTask = new Task(1, "FirstTaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null, null, null, null);
        Task secondTask = new Task(2, "SecondTaskName", "SecondTaskDescription", Group.FRONTEND, Status.DONE, null, null, null, null, null, null);
        tasks.add(firstTask);
        tasks.add(secondTask);
        Page<Task> pagedTasks = new PageImpl(tasks, pageable, 2);

        when(taskRepository.findAll(pageable)).thenReturn(pagedTasks);
        when(taskRepository.findAll(any(Specification.class))).thenReturn(tasks.stream().filter(task -> task.getGroup().equals(Group.BACKEND)).collect(Collectors.toList()));

        //Task Mapper Mock
        TaskResponseDTO firstTaskResponseDTO = new TaskResponseDTO(1, "FirstTaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null);
        TaskResponseDTO secondTaskResponseDTO = new TaskResponseDTO(2, "SecondTaskName", "SecondTaskDescription", Group.FRONTEND, Status.DONE, null, null, null);
        when(taskMapper.toDTO(firstTask)).thenReturn(firstTaskResponseDTO);
        when(taskMapper.toDTO(secondTask)).thenReturn(secondTaskResponseDTO);

        //Call tested method with filterParameters and without
        Page<TaskResponseDTO> filteredTasks = taskService.getAllTasks(filterParametersDTO, 0, 20, "name");
        Page<TaskResponseDTO> allTasks = taskService.getAllTasks(new FilterParametersDTO(), 0, 20, "name");

        //Asserts for filteredTasks
        assertEquals(1L, filteredTasks.getTotalElements());
        assertEquals(firstTaskResponseDTO, filteredTasks.getContent().get(0));

        //Asserts for allTasks
        assertEquals(20, allTasks.getSize());
        assertEquals(2L, allTasks.getTotalElements());
        assertEquals(Sort.by("name").ascending(), allTasks.getSort());
        assertEquals(firstTaskResponseDTO, allTasks.getContent().get(0));
    }

    @Test
    void test_createTask() {
        Task task = new Task();
        task.setId(1);
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();

        doNothing().when(taskMapper).toDomain(any(Task.class), any(TaskRequestDTO.class));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        int savedTaskId = taskService.createTask(taskRequestDTO);

        assertEquals(1, savedTaskId);
    }

    @Test
    void test_getTaskById() {

        Optional<Task> task = Optional.of(new Task(1, "TaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null, null, null, null));
        Optional<Task> emptyTask = Optional.empty();
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO(1, "TaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null);

        when(taskRepository.findById(1)).thenReturn(task);
        when(taskRepository.findById(2)).thenReturn(emptyTask);
        when(taskMapper.toDTO(task.get())).thenReturn(taskResponseDTO);

        TaskResponseDTO taskResponseDTOResult = taskService.getTaskById(1);
        TaskResponseDTO taskResponseDTOResultWithNull = taskService.getTaskById(2);

        assertEquals(taskResponseDTO, taskResponseDTOResult);
        assertNull(taskResponseDTOResultWithNull);
    }

    @Test
    void test_updateTask() {
        Optional<Task> taskCreated = Optional.of(new Task(1, "TaskName", "TaskDescription", Group.BACKEND, Status.CREATED, null, null, null, null, null, null));
        Optional<Task> taskInProgress = Optional.of(new Task(2, "TaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null, null, null, null));
        Optional<Task> taskInTest = Optional.of(new Task(3, "TaskName", "TaskDescription", Group.BACKEND, Status.IN_TEST, null, null, null, null, null, null));
        Optional<Task> taskInDone = Optional.of(new Task(4, "TaskName", "TaskDescription", Group.BACKEND, Status.DONE, null, null, null, null, null, null));
        Optional<Task> emptyTask = Optional.empty();

        TaskRequestDTO taskRequestDTOInProgress = new TaskRequestDTO(1, "TaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null);
        TaskRequestDTO taskRequestDTOInTest = new TaskRequestDTO(2, "TaskName", "TaskDescription", Group.BACKEND, Status.IN_TEST, null, null);
        TaskRequestDTO taskRequestDTODone = new TaskRequestDTO(3, "TaskName", "TaskDescription", Group.BACKEND, Status.DONE, null, null);
        TaskRequestDTO taskRequestDTODoneTwo = new TaskRequestDTO(4, "TaskName", "TaskDescription", Group.BACKEND, Status.DONE, null, null);
        TaskRequestDTO notExistedTaskRequestDTO = new TaskRequestDTO();
        notExistedTaskRequestDTO.setId(5);

        when(taskRepository.findById(1)).thenReturn(taskCreated);
        when(taskRepository.findById(2)).thenReturn(taskInProgress);
        when(taskRepository.findById(3)).thenReturn(taskInTest);
        when(taskRepository.findById(4)).thenReturn(taskInDone);
        when(taskRepository.findById(5)).thenReturn(emptyTask);
        doNothing().when(taskMapper).toDomain(any(Task.class), any(TaskRequestDTO.class));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArguments()[0]);


        Integer inProgressSavedTaskId = taskService.updateTask(taskRequestDTOInProgress);
        Integer inTestSavedTaskId = taskService.updateTask(taskRequestDTOInTest);
        Integer doneSavedTaskId = taskService.updateTask(taskRequestDTODone);
        Integer secondDoneSavedTaskId = taskService.updateTask(taskRequestDTODoneTwo);
        Integer notSavedTaskId = taskService.updateTask(notExistedTaskRequestDTO);


        assertEquals(1, inProgressSavedTaskId);
        assertEquals(2, inTestSavedTaskId);
        assertEquals(3, doneSavedTaskId);
        assertEquals(4, secondDoneSavedTaskId);
        assertNull(notSavedTaskId);

    }
}
