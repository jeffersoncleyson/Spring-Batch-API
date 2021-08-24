package com.poc.springbatch.batch;

import com.poc.springbatch.core.model.Task;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PartitionerMongoDB implements Partitioner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @StepScope
    public Map<String, ExecutionContext> partition(int gridSize) {

        Map<String, ExecutionContext> result = new HashMap<>();

        if(gridSize == 0) return result;

        final var query = new Query().with(PageRequest.of(0, 10));
        final var count = mongoTemplate.count(query.skip(-1).limit(-1), Task.class);
        final var elementsPerPage = (int) count / gridSize;

        if(elementsPerPage != 0 ) {

            Pageable pageable = PageRequest.of(0, elementsPerPage);
            query.with(pageable);
            List<Task> lista = mongoTemplate.find(query, Task.class);
            var taskPage = PageableExecutionUtils.getPage(lista, pageable, () -> count);

            final var totalPages = taskPage.getTotalPages();

            for (int i = 0; i < totalPages; i++) {
                ExecutionContext value = new ExecutionContext();
                result.put("partition_" + i, value);
                value.putInt("page", i);
                value.putInt("pageSize", elementsPerPage);
            }
        }

        return result;
    }
}
