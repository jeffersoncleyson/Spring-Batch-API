package com.poc.springbatch.batch;

import com.poc.springbatch.core.model.Task;
import com.poc.springbatch.repository.TaskRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WriterMongoDB implements ItemWriter<Task> {

    @Autowired
    private TaskRepository repository;

    @Override
    public void write(List<? extends Task> consents) throws Exception {
        repository.saveAll(consents);
    }
}
