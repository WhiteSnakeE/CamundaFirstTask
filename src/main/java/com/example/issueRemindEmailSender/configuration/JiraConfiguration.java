package com.example.issueRemindEmailSender.configuration;

import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;

@Configuration
@Profile({"dev"})
public class JiraConfiguration {

    @Bean
    public JiraRestClient jiraRestClient(
            @Value("${jira.user}") String jiraUser,
            @Value("${jira.password}") String jiraPassword,
            @Value("${jira.url}") String url) {
        AsynchronousJiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        AuthenticationHandler authenticationHandler = new BasicHttpAuthenticationHandler(jiraUser, jiraPassword);
        return factory.createWithAuthenticationHandler(URI.create(url), authenticationHandler);

    }
}
