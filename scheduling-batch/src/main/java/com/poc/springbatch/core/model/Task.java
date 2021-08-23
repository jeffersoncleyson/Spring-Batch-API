package com.poc.springbatch.core.model;

import com.poc.springbatch.core.enums.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Document(collection = "task")
public class Task {

	@Id
	private String id;

	@NotNull
	@NotEmpty
	private String taskRef;

	@NotNull
	private String description;

	@NotNull
	@Builder.Default
	private StateEnum status = StateEnum.WAITING;

	@Column(updatable = false)
	private Date createdDate;

	private Date lastModifiedDate;

}
