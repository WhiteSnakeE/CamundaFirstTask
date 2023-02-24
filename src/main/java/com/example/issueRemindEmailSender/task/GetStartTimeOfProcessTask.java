package com.example.issueRemindEmailSender.task;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@Component("GetStartTimeOfProcess")
public class GetStartTimeOfProcessTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        log.info("Start time of this event is {}", dtf.format(now));


    }
}


