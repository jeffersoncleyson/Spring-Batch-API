package com.poc.springbatch.controller;

import com.poc.springbatch.service.TaskService;
import com.poc.springbatch.core.dto.TaskDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/task")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> saveTask(@RequestBody TaskDTO taskDTO) {
        final var taskExists = taskService.findTaskRef(taskDTO.getTaskRef());
        if(taskExists != null) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        return ResponseEntity.ok(taskService.save(taskDTO));
    }

    @GetMapping(value = "/{taskRef}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable String taskRef) {
        final var task = taskService.findTaskRef(taskRef);
        if(task != null) return ResponseEntity.ok(task);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        final var task = taskService.getAllTasks();
        if(task != null) return ResponseEntity.ok(task);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{taskRef}")
    public ResponseEntity<TaskDTO> deleteTaskRef(@PathVariable String taskRef) {
        final var task = taskService.deleteTask(taskRef);
        if(task != null) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
