package com.craftsoft.taskmanagementapi.controller.integration;

import com.craftsoft.taskmanagementapi.dao.SubTaskRepository;
import com.craftsoft.taskmanagementapi.dao.TaskRepository;
import com.craftsoft.taskmanagementapi.JsonUtil;
import com.craftsoft.taskmanagementapi.TaskManagementApiApplication;
import com.craftsoft.taskmanagementapi.domain.SubTask;
import com.craftsoft.taskmanagementapi.domain.Task;
import com.craftsoft.taskmanagementapi.domain.enums.Group;
import com.craftsoft.taskmanagementapi.domain.enums.Status;
import com.craftsoft.taskmanagementapi.dto.SubTaskRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TaskManagementApiApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "/application-test.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SubTaskControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void test_createSubTask_created() throws Exception {
        Task task = new Task(1, "TestTaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null, null, null, null);
        taskRepository.save(task);
        SubTaskRequestDTO subtaskRequestDTO = new SubTaskRequestDTO(1, "TestSubTaskName", "TestSubTaskDescription", Status.IN_PROGRESS, task);

        mvc.perform(post("/api/v1/subTasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(subtaskRequestDTO)))
                .andExpect(status().isCreated());

        List<SubTask> found = subTaskRepository.findAll();
        assertThat(found).extracting(SubTask::getName).contains("TestSubTaskName");
    }

    @Test
    public void test_createSubTask_badRequest() throws Exception {
        Task task = new Task(1, "TestTaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null, null, null, null);
        taskRepository.save(task);
        SubTaskRequestDTO subtaskRequestDTO = new SubTaskRequestDTO(1, "TestSubTaskName", "", Status.IN_PROGRESS, task);

        mvc.perform(post("/api/v1/subTasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(subtaskRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_getSubTaskById_badRequest() throws Exception {
        mvc.perform(get("/api/v1/subTasks/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_getSubTaskById_ok() throws Exception {
        mvc.perform(get("/api/v1/subTasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Refactor create")));
    }

    @Test
    public void test_getSubTaskById_notFound() throws Exception {
        mvc.perform(get("/api/v1/subTasks/9"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubTask_notFound() throws Exception {
        Task task = new Task(1, "TestTaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null, null, null, null);
        taskRepository.save(task);
        SubTaskRequestDTO subTaskRequestDTO = new SubTaskRequestDTO(9, "TestSubTaskName", "TestSubTaskDescription", Status.IN_PROGRESS, task);

        mvc.perform(put("/api/v1/subTasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(subTaskRequestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateSubTask_noContent() throws Exception {
        Task task = new Task(1, "TestTaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null, null, null, null);
        taskRepository.save(task);
        SubTaskRequestDTO subTaskRequestDTO = new SubTaskRequestDTO(1, "TestSubTaskName", "TestSubTaskDescription", Status.IN_PROGRESS, task);

        mvc.perform(put("/api/v1/subTasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(subTaskRequestDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateSubTask_badRequest() throws Exception {
        Task task = new Task(1, "TestTaskName", "TaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null, null, null, null, null);
        taskRepository.save(task);
        SubTaskRequestDTO subTaskRequestDTO = new SubTaskRequestDTO(1, "", "TestSubTaskDescription", Status.IN_PROGRESS, task);

        mvc.perform(put("/api/v1/subTasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(subTaskRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSubTask_badRequest() throws Exception {
        mvc.perform(delete("/api/v1/subTasks/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSubTask_noContent() throws Exception {
        mvc.perform(delete("/api/v1/subTasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTask_notFound() throws Exception {
        mvc.perform(delete("/api/v1/subTasks/9"))
                .andExpect(status().isNotFound());
    }
}
