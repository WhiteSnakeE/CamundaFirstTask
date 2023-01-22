package com.example.firstCamundaTask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JiraIssue implements Serializable {
	private Integer id;
	private DateTime date;
	private String statusName;
}
