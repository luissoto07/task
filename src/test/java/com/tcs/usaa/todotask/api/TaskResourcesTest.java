package com.tcs.usaa.todotask.api;

import com.tcs.usaa.todotask.domain.Datos;
import com.tcs.usaa.todotask.domain.Task;
import com.tcs.usaa.todotask.exceptions.ResourceNotFoundException;
import com.tcs.usaa.todotask.repo.TaskRepo;
import com.tcs.usaa.todotask.service.TaskService;
import com.tcs.usaa.todotask.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskResources.class)
class TaskResourcesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;
    @MockBean
    private TaskRepo taskRepo;
    @MockBean
    private UserService userService;

    @Test
    void createTask() throws Exception {
        Task task = new Task(null, "Prueba de creación", "Prueba con Junit", new Date(), new Date(), "Yes", "ON_TIME");
        when(taskService.save(any())).then(invocation -> {
            Task t = invocation.getArgument(0);
            t.setId(4L);
            return t;
        });

        mockMvc.perform(post("/task/create").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

//        verify(taskService).save(any());
    }

    @Test
    void getTasks() throws Exception {
        List<Task> tasks = Arrays.asList(
                Datos.task001().orElseThrow(() -> new ResourceNotFoundException("Task ", "id", 1L)),
                Datos.task002().orElseThrow(() -> new ResourceNotFoundException("Task ", "id", 2L)),
                Datos.task003().orElseThrow(() -> new ResourceNotFoundException("Task ", "id", 3L))
        );
        when(taskService.getTasks()).thenReturn(tasks);
        mockMvc.perform(get("/task/getall").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Primera tarea spring"))
                .andExpect(jsonPath("$[1].title").value("Segunda tarea spring"))
                .andExpect(jsonPath("$[2].title").value("Tercera tarea spring"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(taskService).getTasks();
    }

    @Test
    void getTask() throws Exception {
        when(taskService.getTask(1L)).thenReturn(Datos.task001().orElseThrow(() -> new ResourceNotFoundException("Task ", "id", 1L)));
        mockMvc.perform(get("/task/get/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Primera tarea spring")
                );
        verify(taskService).getTask(1L);
    }

}