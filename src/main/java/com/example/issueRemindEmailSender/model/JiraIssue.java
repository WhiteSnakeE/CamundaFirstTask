package com.example.issueRemindEmailSender.model;

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
	private Long id;
	private DateTime updateDate;
	private DateTime createDate;
	private String email;
	private String statusName;
	private int delta;
}
