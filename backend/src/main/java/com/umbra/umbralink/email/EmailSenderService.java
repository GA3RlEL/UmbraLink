package com.umbra.umbralink.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;

    @Value("${SMTP_EMAIL}")
    private String sentFrom;

    public EmailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail, String subject,String body) throws MessagingException {
        SimpleMailMessage email = new SimpleMailMessage();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(sentFrom);
        helper.setTo(toEmail);
        helper.setText(body, true);
        helper.setSubject(subject);
        mailSender.send(message);
    }
}
