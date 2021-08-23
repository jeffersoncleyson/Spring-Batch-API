package com.poc.springbatch.service;

import com.poc.springbatch.core.dto.TaskDTO;
import com.poc.springbatch.core.mapper.TaskMapper;
import com.poc.springbatch.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    public TaskDTO save(TaskDTO taskDTO){
        final var task = taskMapper.taskDTOToTask(taskDTO);
        task.setCreatedDate(new Date());
        task.setLastModifiedDate(new Date());
        final var taskSave = taskRepository.save(task);
        return taskMapper.taskToTaskDTO(taskSave);
    }

    public TaskDTO findTaskRef(String taskRef){
        final var task = taskRepository.findByTaskRef(taskRef);
        if(task.isPresent()) return taskMapper.taskToTaskDTO(task.get());
        return null;
    }

    public List<TaskDTO> getAllTasks(){
        final var task = taskRepository.findAll();
        return taskMapper.listTaskToListTaskDTO(task);
    }

    public TaskDTO deleteTask(String taskRef){
        final var task = taskRepository.findByTaskRef(taskRef);
        task.ifPresent(value -> taskRepository.delete(value));
        return task.isPresent() ? taskMapper.taskToTaskDTO(task.get()) : null;
    }
}
