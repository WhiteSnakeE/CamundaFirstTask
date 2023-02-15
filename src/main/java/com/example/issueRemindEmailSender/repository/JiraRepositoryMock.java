package com.example.issueRemindEmailSender.repository;

import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.example.issueRemindEmailSender.model.JiraIssue;


import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;


import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;


//@Repository
//@Profile({"dev"})
public class JiraRepositoryMock implements JiraRepository{



    public SearchResult getIssuesFields(String jql, int maxResults) {
        return null;
    }


}
