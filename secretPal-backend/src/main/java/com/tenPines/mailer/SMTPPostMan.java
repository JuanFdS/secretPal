package com.tenPines.mailer;

import com.tenPines.model.Worker;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class SMTPPostMan implements PostMan {


    private Properties authProperties;
    private Properties templateProperties;

    public SMTPPostMan(Properties authProperties, Properties templateProperties){
        this.authProperties = authProperties;
        this.templateProperties = templateProperties;
    }


    private Session getAuthenticatedSession() {
        String user = authProperties.getProperty("auth.user");
        String password = authProperties.getProperty("auth.password");
        Session session = Session.getInstance(authProperties,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });
        return session;
    }

    private Message createEmptyMessage() {
        return new MimeMessage(getAuthenticatedSession());
    }


    private Message fillEMailFor(String sender, String subject, String bodyText) throws MessagingException {
        Message message = createEmptyMessage();
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sender));
        message.setSubject(subject);
        message.setText(bodyText);
        return message;
    }

    protected void sendMessage(Message message) throws MessagingException, IOException {
            new Thread(() -> {
                try {
                    Transport.send(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }).start();
    }

    private String assignationSubject(){
        return templateProperties.getProperty("mail.subject");
    }

    private String assignationBodyText(Worker receiver) {
        templateProperties.setProperty("receiver.fullName",receiver.getFullName());
        templateProperties.setProperty("receiver.dateOfBirth", receiver.getDateOfBirth().toString());
        return templateProperties.getProperty("mail.bodyText");
    }

    @Override
    public void notifyPersonWithSecretPalInformation(Worker participant, Worker secretPal) throws MessagingException, IOException {
        Message aMessage = fillEMailFor(participant.geteMail(), assignationSubject(),assignationBodyText(secretPal));
        sendMessage(aMessage);
    }


}