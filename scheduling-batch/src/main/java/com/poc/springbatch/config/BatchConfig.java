package com.poc.springbatch.config;

import com.poc.springbatch.batch.PartitionerMongoDB;
import com.poc.springbatch.batch.ReaderMongoDB;
import com.poc.springbatch.core.model.Task;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class BatchConfig {

    @Autowired
    private PartitionerMongoDB partitionerMongoDB;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ReaderMongoDB itemReader;

    @Autowired
    private ItemProcessor<Task, Task> itemProcessor;

    @Autowired
    private ItemWriter<Task> itemWriter;

    @Bean
    public Job job() {

        Step stepBack = stepBuilderFactory.get("Scheduling-MongoDB")
                .<Task, Task>chunk(10)
                .reader(itemReader.read(null, null))
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        Step step = stepBuilderFactory.get("MongoDB-Partitioner")
                .partitioner(stepBack.getName(), partitionerMongoDB)
                .step(stepBack)
                .gridSize(10)
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();

        return jobBuilderFactory.get("MongoDB-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }


}