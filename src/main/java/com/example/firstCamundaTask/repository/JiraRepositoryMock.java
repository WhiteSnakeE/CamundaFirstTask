package com.example.firstCamundaTask.repository;

import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.example.firstCamundaTask.model.JiraIssue;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
//@Profile({"dev"})
public class JiraRepositoryMock implements JiraRepository{



    public SearchResult getIssuesFields(String jql, int maxResults) {
        return null;
    }


}
