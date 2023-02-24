package com.example.issueRemindEmailSender.service;

import com.example.issueRemindEmailSender.repository.SendEmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendEmailService {


    private final SendEmailRepository sendEmailRepository;

    public SendEmailService(SendEmailRepository sendEmailRepository) {
        this.sendEmailRepository = sendEmailRepository;
    }

    public boolean send(String receiveEmail, String messageText) {
        boolean isSended;
        isSended = sendEmailRepository.send(receiveEmail, messageText);
        if (!isSended){
            log.info("Message was not send");
            throw new BpmnError("SOLVIT_ERROR");
        }
        return true;
    }


}

