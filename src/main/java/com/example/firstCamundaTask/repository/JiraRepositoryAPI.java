package com.example.firstCamundaTask.repository;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.request.GetRequest;
import org.springframework.stereotype.Repository;

@Repository
public class JiraRepositoryAPI implements JiraRepositoryRequest{

    private final HttpResponse<JsonNode> httpResponse;

    public JiraRepositoryAPI(HttpResponse<JsonNode> httpResponse) {
        this.httpResponse = httpResponse;
    }
    @Override
    public HttpResponse<JsonNode> getIssueFields() {
       return httpResponse;
    }


}
