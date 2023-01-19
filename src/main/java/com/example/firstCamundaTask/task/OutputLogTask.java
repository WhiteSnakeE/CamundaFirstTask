package com.example.firstCamundaTask.task;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@Component("OutputLogTask")
public class OutputLogTask implements JavaDelegate {


    @Override
    public void execute(DelegateExecution execution) throws Exception {
//        execution.getProcessEngineServices().getRuntimeService()
//                .createMessageCorrelation("Message_1ip71p2").correlate();

//        runtimeService.startProcessInstanceById("Activity_1qbrr2o");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        log.info("Start time of this event is {}",dtf.format(now));

//        runtimeService.correlateMessage("Message_1ip71p2");
//        log.info("Start time of this event is {}","started");

    }
}


