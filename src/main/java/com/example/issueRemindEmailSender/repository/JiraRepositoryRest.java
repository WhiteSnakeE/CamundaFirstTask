package com.example.issueRemindEmailSender.repository;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.SearchRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;


@Repository
@Profile({"dev"})
public class JiraRepositoryRest implements JiraRepository {


    private final SearchRestClient searchRestClient;

    public JiraRepositoryRest(JiraRestClient jiraRestClient) {
        searchRestClient = jiraRestClient.getSearchClient();
    }

    @Override
    public SearchResult getIssuesFields(String jql, int maxResults) {
        try {
            return searchRestClient.searchJql(jql, maxResults, 0, null).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
