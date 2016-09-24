package com.tenPines.model;

import com.tenPines.utils.PropertyParser;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class Message {

    private String recipient;
    private String body;
    private String subject;

    public Message() {
    }

    public static Message create(String recipient, String subject, String body) {
        Message instance = new Message();
        instance.recipient = recipient;
        instance.body = body;
        instance.subject = subject;
        return instance;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public javax.mail.Message toJavax() throws MessagingException {
        javax.mail.Message javaxMessage = new MimeMessage(authenticatedSession());
        javaxMessage.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(getRecipient()));
        javaxMessage.setSubject(getSubject());
        javaxMessage.setText(getBody());
        return javaxMessage;
    }

    private Session authenticatedSession() {
        Properties authProperties = new Properties();
        try {
            authProperties = new PropertyParser("src/main/resources/gmail.properties");
        } catch (IOException e) {
            authProperties.setProperty("auth.user", "default");
            authProperties.setProperty("auth.password", "default");
        }

        String user = authProperties.getProperty("auth.user");
        String password = authProperties.getProperty("auth.password");
        return Session.getInstance(authProperties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                });
    }
}
