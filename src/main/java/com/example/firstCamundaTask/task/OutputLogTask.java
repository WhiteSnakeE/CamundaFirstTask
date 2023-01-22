package com.example.firstCamundaTask.task;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.model.dmn.Dmn;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@Component("OutputLogTask")
public class OutputLogTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        log.info("Start time of this event is {}",dtf.format(now));


    }
}


