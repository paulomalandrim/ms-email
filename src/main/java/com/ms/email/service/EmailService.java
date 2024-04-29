package com.ms.email.service;

import com.ms.email.enums.StatusEmail;
import com.ms.email.model.EmailModel;
import com.ms.email.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel){
        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(emailModel.getEmailTo());
            simpleMailMessage.setSubject(emailModel.getSubject());
            simpleMailMessage.setText(emailModel.getText());

            javaMailSender.send(simpleMailMessage);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException ex) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }

    }
}
