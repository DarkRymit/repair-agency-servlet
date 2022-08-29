package com.epam.finalproject.service.impl;

import com.epam.finalproject.exceptions.MailException;
import com.epam.finalproject.framework.web.annotation.Service;
import com.epam.finalproject.model.entity.PasswordResetToken;
import com.epam.finalproject.model.entity.VerificationToken;
import com.epam.finalproject.model.event.OnPasswordResetEvent;
import com.epam.finalproject.model.event.OnRegistrationCompleteEvent;
import com.epam.finalproject.service.EmailService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

@Service
public class SunEmailService implements EmailService {

    private final Session messageSession;

    private final Properties properties;

    public SunEmailService(Session messageSession, Properties properties) {
        this.messageSession = messageSession;
        this.properties = properties;
    }

    @Override
    public MimeMessage createMimeMessage() {
        return new MimeMessage(messageSession);
    }

    @Override
    public void send(MimeMessage message) throws MailException {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }

    @Override
    public void sendVerificationLetter(OnRegistrationCompleteEvent event,
            VerificationToken token) throws MailException {

        try {
            MimeMessage message = createMimeMessage();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(event.getUser().getEmail()));
            message.setSubject("Verification");
            message.setText(properties.getProperty("mail.appUrl")+"/auth/confirmRegister/" + token.getToken());
            send(message);
        } catch (MessagingException e) {
            throw new MailException(e);
        }

    }

    @Override
    public void sendPasswordResetLetter(OnPasswordResetEvent event, PasswordResetToken token) throws MailException {
        try {
            MimeMessage message = createMimeMessage();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(event.getUser().getEmail()));
            message.setSubject("Password reset");
            message.setText(properties.getProperty("mail.appUrl")+"/auth/resetpassword/confirm/" + token.getToken());
            send(message);
        } catch (MessagingException e) {
            throw new MailException(e);
        }
    }


}
