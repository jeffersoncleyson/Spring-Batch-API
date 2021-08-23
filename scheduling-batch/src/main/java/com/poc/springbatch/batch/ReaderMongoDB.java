package com.poc.springbatch.batch;

import com.poc.springbatch.core.enums.StateEnum;
import com.poc.springbatch.core.model.Task;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
public class ReaderMongoDB implements ItemReader<MongoItemReader<Task>> {

    @Autowired
    private MongoTemplate mongoTemplate;
    private String taskExpireTime = "PT60M";
    private static final String TASK_FIELD_STATUS = "status";

    @Override
    @Bean
    public MongoItemReader<Task> read() {

        MongoItemReader<Task> itemReader = new MongoItemReader<>();
        itemReader.setTemplate(mongoTemplate);
        itemReader.setTargetType(Task.class);
        itemReader.setQuery(queryDatabase());

        return itemReader;
    }

    private Query queryDatabase(){

        var expireTimeInMinutes = Duration.parse(taskExpireTime).toMinutes();
        var timeAgo = ZonedDateTime.now(ZoneOffset.UTC).minusMinutes(expireTimeInMinutes);

        var criteria = new Criteria();
        criteria.andOperator(Criteria.where(TASK_FIELD_STATUS).is(StateEnum.WAITING), Criteria.where("createdDate").lt(timeAgo));

        final var query = new Query();
        query.addCriteria(criteria);

        return query;
    }
}
