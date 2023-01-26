package com.example.firstCamundaTask.configuration;

import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;

@Configuration
@Profile({"dev"})
public class JiraConfigurationAPI {
    @Value(value = "${jira.user}") private String jiraUser;
    @Value(value = "${jira.password}") private String jiraPassword;
    @Value(value = "${jira.url}")  private  String url;

    @Bean
    public HttpResponse<JsonNode> sendRequest() throws UnirestException {
        String request = url;
        return Unirest.get(request)
                .header("Accept", "application/json")
                .basicAuth(jiraUser, jiraPassword)
                .asJson();
    }
}
