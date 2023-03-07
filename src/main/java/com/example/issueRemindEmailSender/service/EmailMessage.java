package com.example.issueRemindEmailSender.service;

import com.example.issueRemindEmailSender.model.JiraIssue;

public class EmailMessage {


    public static String setMessageEmployee(JiraIssue jiraIssue) {
        return "Hello, You have not been updating your issues for " + JiraService.lastUpdateDays(jiraIssue) + " days  \n" +
                "Issue self: " + jiraIssue.getSelf() + "\n" +
                "Issue start date: " + jiraIssue.getCreateDate() + "\n" +
                "Issue update date: " + jiraIssue.getUpdateDate() + "\n" +
                "Issue status: " + jiraIssue.getStatusName();
    }


    public static String setMessageBoss(JiraIssue jiraIssue) {
        return "Hello, this employee " + jiraIssue.getEmail() +
                " has not been updating his issues for " + JiraService.lastUpdateDays(jiraIssue) + " days  \n" +
                "Issue self: " + jiraIssue.getSelf() + "\n" +
                "Issue start date: " + jiraIssue.getCreateDate() + "\n" +
                "Issue update date: " + jiraIssue.getUpdateDate() + "\n" +
                "Issue status: " + jiraIssue.getStatusName();
    }
}
