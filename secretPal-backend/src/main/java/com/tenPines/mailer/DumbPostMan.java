package com.tenPines.mailer;

import javax.mail.Message;
import javax.mail.MessagingException;
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

    public boolean containsMessageWith(String subject, String bodyText) throws java.io.IOException, javax.mail.MessagingException {
        return messages.stream().anyMatch(message -> messageContainsInformation(message, subject, bodyText));
    }

    public boolean containsMessageTo(String to) {
        return messages.stream().anyMatch(message -> messageHasTo(message, to));
    }

    private boolean messageHasTo(Message message, String to) {
        boolean ret = false;
        try {
            ret = message.getAllRecipients()[0].toString().contains(to);
        } catch (MessagingException e) {
            e.printStackTrace(); //TODO: Ewwww, catchear la excepcion acá (stream no puede tirar excepcion)
        }
        return ret;
    }

    private boolean messageContainsInformation(Message message, String subject, String body) {
        boolean ret = false;
        try {
            ret |= message.getSubject().contains(subject) &&
                    message.getContent().toString().contains(body);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
