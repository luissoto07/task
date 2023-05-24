package com.tcs.usaa.todotask.service;

import com.tcs.usaa.todotask.domain.Task;
import com.tcs.usaa.todotask.exceptions.ResourceNotFoundException;
import com.tcs.usaa.todotask.repo.TaskRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    @Override
    public Task save(Task task) {
        log.info("Saving new task {} to the db", task.getTitle());
        return taskRepo.save(task);
    }

    @Override
    public List<Task> getTasks() {
        log.info("Fetching all tasks ");
        return taskRepo.findAll();
    }

    @Override
    public Task getTask(Long id) {
        log.info("Fetching task with {} id...", id);
        return taskRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task","id",id));
    }

    @Override
    public List<Task> getByStatus() {
        log.info("Fetching task ordered by status");
        return taskRepo.findAll().stream().filter(task -> task.getStatus().equals("LATE")).collect(Collectors.toList());
    }

    @Override
    public Task updateTask(Long id, Task task) {
        log.info("Updating {} task ", id);
        Task myTask = taskRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Task","id",id));
        myTask.setTitle(task.getTitle());
        myTask.setDescription(task.getDescription());
        myTask.setCompletionDate(task.getCompletionDate());
        myTask.setStatus(task.getStatus());
        return taskRepo.save(myTask);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepo.deleteById(id);
    }
}
