package com.poc.springbatch.config;

import com.poc.springbatch.core.model.Task;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   MongoItemReader<Task> itemReader,
                   ItemProcessor<Task, Task> itemProcessor,
                   ItemWriter<Task> itemWriter) {


        Step step = stepBuilderFactory.get("Scheduling-MongoDB")
                .<Task, Task>chunk(10)
                .reader(itemReader)
                //.processor(itemProcessor)
                .writer(itemWriter)
                .build();


        return jobBuilderFactory.get("MongoDB-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }


}