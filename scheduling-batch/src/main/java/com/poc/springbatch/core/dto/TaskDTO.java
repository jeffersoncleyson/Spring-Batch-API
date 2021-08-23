package com.poc.springbatch.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poc.springbatch.core.enums.StateEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Builder
public class TaskDTO {

    @NotNull
    @NotEmpty
    private String taskRef;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @Builder.Default
    private StateEnum status = StateEnum.WAITING;

    @Column(updatable = false)
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date createdDate;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date lastModifiedDate;
}
