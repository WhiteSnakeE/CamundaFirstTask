package com.example.issueRemindEmailSender.repository;

import com.atlassian.jira.rest.client.api.domain.SearchResult;

//@Repository
//@Profile({"dev"})
public class JiraRepositoryMock implements JiraRepository{



    public SearchResult getIssuesFields(String jql, int maxResults) {
        return null;
    }


}
