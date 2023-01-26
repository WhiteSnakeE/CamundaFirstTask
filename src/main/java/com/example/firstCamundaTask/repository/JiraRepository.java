package com.example.firstCamundaTask.repository;

import com.atlassian.jira.rest.client.api.domain.SearchResult;


public interface JiraRepository {

    SearchResult getIssuesFields(String jql, int maxResults);

}
