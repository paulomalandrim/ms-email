package com.ms.email.consumer;

import com.ms.email.dto.EmailRecordDto;
import com.ms.email.model.EmailModel;
import com.ms.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto email){
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(email, emailModel);
        emailService.sendEmail(emailModel);

    }
}
