package com.craftsoft.taskmanagementapi.controller.integration;

import com.craftsoft.taskmanagementapi.TaskManagementApiApplication;
import com.craftsoft.taskmanagementapi.dao.TaskRepository;
import com.craftsoft.taskmanagementapi.domain.Task;
import com.craftsoft.taskmanagementapi.JsonUtil;
import com.craftsoft.taskmanagementapi.domain.enums.Group;
import com.craftsoft.taskmanagementapi.domain.enums.Status;
import com.craftsoft.taskmanagementapi.dto.TaskRequestDTO;
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

import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TaskManagementApiApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestPropertySource(locations = "/application-test.properties")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskControllerIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TaskRepository repository;

    @Test
    public void test_createTask_created() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(1, "TestTaskName", "TestTaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null);

        mvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(taskRequestDTO)))
                .andExpect(status().isCreated());

        List<Task> found = repository.findAll();
        assertThat(found).extracting(Task::getName).contains("TestTaskName");
    }

    @Test
    public void test_createTask_badRequest() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(1, "", "TestTaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null);

        mvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(taskRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_getTaskById_badRequest() throws Exception {
        mvc.perform(get("/api/v1/tasks/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_getTaskById_ok() throws Exception {
        mvc.perform(get("/api/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Create header")));
    }

    @Test
    public void test_getTaskById_notFound() throws Exception {
        mvc.perform(get("/api/v1/tasks/9"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_getAllTasks_noContent() throws Exception {
        repository.deleteAll();
        mvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void test_getAllTasks_badRequest() throws Exception {
        mvc.perform(get("/api/v1/tasks/?pageSize=0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_getAllTasks_ok() throws Exception {
        mvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(equalTo(8))))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content[0].name", is("Clarify requirements")))
                .andExpect(jsonPath("content[1].name", is("Create endpoint")));
    }

    @Test
    public void test_getAllTasks_sortedByDescription() throws Exception {
        mvc.perform(get("/api/v1/tasks?sortField=description"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(equalTo(8))))
                .andExpect(jsonPath("content[0].description", is("Need to clarify main page mockup")))
                .andExpect(jsonPath("content[7].description", is("Refactor currency service")));
    }

    @Test
    public void test_getAllTasks_secondPage() throws Exception {
        mvc.perform(get("/api/v1/tasks?page=1&pageSize=4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(equalTo(4))))
                .andExpect(jsonPath("content[0].name", is("Create table")));
    }

    @Test
    public void test_getAllTasks_filtered() throws Exception {
        mvc.perform(get("/api/v1/tasks?status=CREATED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("content", hasSize(equalTo(1))))
                .andExpect(jsonPath("content[0].name", is("Create header")))
                .andExpect(jsonPath("content[0].assignee", is("Kevin Durant")));
    }

    @Test
    public void updateTask_notFound() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(9, "NewTask", "TestTaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null);

        mvc.perform(put("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(taskRequestDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTask_noContent() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(1, "NewTask", "TestTaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null);

        mvc.perform(put("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(taskRequestDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void updateTask_badRequest() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(1, "", "TestTaskDescription", Group.BACKEND, Status.IN_PROGRESS, null, null);

        mvc.perform(put("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(taskRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteTask_badRequest() throws Exception {
        mvc.perform(delete("/api/v1/tasks/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteTask_noContent() throws Exception {
        mvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteTask_notFound() throws Exception {
        mvc.perform(delete("/api/v1/tasks/9"))
                .andExpect(status().isNotFound());
    }
}
