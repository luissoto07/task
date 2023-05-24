package com.tcs.usaa.todotask.api;

import com.tcs.usaa.todotask.domain.Task;
import com.tcs.usaa.todotask.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskResources {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Task> createTask(Task task){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return  ResponseEntity.created(uri).body(taskService.save(task));
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Task>> getTasks(){
        return  ResponseEntity.ok().body(taskService.getTasks());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Task> getTask(@PathVariable(name="id")Long id){
        return ResponseEntity.ok().body(taskService.getTask(id));
    }

    @GetMapping("/bystatus")
    public ResponseEntity<List<Task>> getTaskFilterByStatus(){
        return ResponseEntity.ok().body(taskService.getByStatus());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable(name="id") Long id, @RequestBody Task task){
        return ResponseEntity.ok().body(taskService.updateTask(id,task));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable(name="id")Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
