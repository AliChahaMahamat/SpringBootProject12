package rw.academics.OnlineBankingSystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final MailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(MailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (MailException e) {
            logger.error("An error occurred while sending email: {}", e.getMessage());
            logger.error("Stack trace: ", e);
            throw new RuntimeException("Email sending failed: " + e.getMessage());
        }
    }
}
