package com.poc.springbatch.batch;

import com.poc.springbatch.core.model.Task;
import com.poc.springbatch.core.enums.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProcessorMongoDB implements ItemProcessor<Task, Task> {

    @Override
    public Task process(Task task) throws Exception {

        //log.info("Processing task with id : {} changing state {} to {}", task.getId(), task.getStatus(), StateEnum.RUNNING);

        task.setStatus(StateEnum.RUNNING);

        return task;
    }
}
