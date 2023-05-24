package com.tcs.usaa.todotask.service;

import com.tcs.usaa.todotask.domain.Task;
import com.tcs.usaa.todotask.repo.TaskRepo;

import java.util.List;

public interface TaskService {

    Task save (Task task);
    List<Task> getTasks();
    Task getTask(Long id);
    List<Task> getByStatus();
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
}
