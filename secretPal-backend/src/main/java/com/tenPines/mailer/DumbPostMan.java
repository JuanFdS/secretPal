package com.tenPines.mailer;

import javax.mail.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.mockito.Mockito.mock;

public class DumbPostMan extends SMTPPostMan {

    private static List<Message> messages = new ArrayList<>();

    public DumbPostMan(Properties templateProperties) {
        super(mock(Properties.class), templateProperties);
    }


    @Override
    protected void sendMessage(Message message) throws MessagingException, IOException {
        messages.add(message);
    }

    public boolean containsMessageWith(String subject, String bodyText) throws java.io.IOException, javax.mail.MessagingException{
        return messages.stream().anyMatch(message -> messageContainsInformation(message,subject,bodyText));
    }

    private boolean messageContainsInformation(Message message, String subject, String body) {
            boolean ret = false;
        try {
            ret |= message.getSubject().equals(subject) && message.getContent().equals(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
