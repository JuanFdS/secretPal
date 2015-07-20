package com.tenPines.mailer;

import com.tenPines.model.Worker;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;

public class SMTPPostMan implements PostMan {


    private Properties addressProperties;
    private Properties templateProperties;

    public SMTPPostMan(Properties addressProperties, Properties templateProperties){
        this.addressProperties = addressProperties;
        this.templateProperties = templateProperties;
    }


    private Session getAuthenticatedSession() {
        String user = addressProperties.getProperty("auth.user");
        String password = addressProperties.getProperty("auth.password");
        Session session = Session.getInstance(addressProperties,
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


    private Message fillEMailFor(String sender, String receiver, String subject, String bodyText) throws MessagingException {
        Message message = createEmptyMessage();
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
        message.setSubject(subject);
        message.setText(bodyText);
        return message;
    }

    protected void sendMessage(Message message) throws MessagingException, IOException {
            Transport.send(message);
    }

    private String assignationSubject(){
        return templateProperties.getProperty("mail.subject");
    }

    private String assignationBodyText(Worker receiver) {
        templateProperties.setProperty("receiver.fullName",receiver.getFullName());
        templateProperties.setProperty("receiver.dateOfBirth", receiver.getDateOfBirth().toString() );
        return templateProperties.getProperty("mail.bodyText");
    }

    @Override
    public void notifyPersonWithSecretPalInformation(Worker participant, Worker secretPal) throws MessagingException, IOException {
        Message aMessage = fillEMailFor(participant.geteMail(), secretPal.geteMail(), assignationSubject(),assignationBodyText(secretPal));
        sendMessage(aMessage);
    }



}