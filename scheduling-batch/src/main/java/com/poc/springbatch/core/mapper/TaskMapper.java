package com.poc.springbatch.core.mapper;

import com.poc.springbatch.core.dto.TaskDTO;
import com.poc.springbatch.core.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    TaskDTO taskToTaskDTO(Task task);
    Task taskDTOToTask(TaskDTO taskDTO);

    List<TaskDTO> listTaskToListTaskDTO(List<Task> task);

}
