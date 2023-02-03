package com.example.issueRemindEmailSender.repository;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public interface JiraRepositoryRequest {
    HttpResponse<JsonNode> getIssueFields();

    String getId(int JSONObject);

    String getCreateDate(int JSONObject);

    String getUpdateDate(int JSONObject);

    String getEmail(int JSONObject);

    String getStatus(int JSONObject);

    int getTotalIssues();

}
