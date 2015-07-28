package com.tenPines.model;

import com.tenPines.builder.PropertyBuilder;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;
import java.util.Properties;

@Entity
@Table
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    private String recipient;
    private String body;
    private String subject;
    private String content;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        Properties authProperties = null;
        try {
            authProperties = PropertyBuilder.buildPropertyFrom("src/main/resources/gmail.properties");
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
