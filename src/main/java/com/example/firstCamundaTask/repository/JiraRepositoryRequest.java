package com.example.firstCamundaTask.repository;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public interface JiraRepositoryRequest {
    HttpResponse<JsonNode> getIssueFields();


}
