package com.example.issueRemindEmailSender.repository;

import com.atlassian.jira.rest.client.api.domain.SearchResult;


public interface JiraRepository {

    SearchResult getIssuesFields(String jql, int maxResults);

}
