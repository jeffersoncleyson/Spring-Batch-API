package com.poc.springbatch.repository;

import com.poc.springbatch.core.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends BaseRepository<Task, String> {

    Optional<Task> findByTaskRef(String taskRef);
    List<Task> findAll();

}
