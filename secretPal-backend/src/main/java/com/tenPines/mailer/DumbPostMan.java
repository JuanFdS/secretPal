package com.tenPines.mailer;

import com.tenPines.model.Message;

import java.util.ArrayList;
import java.util.List;

public class DumbPostMan implements PostMan {

    private static List<Message> messages = new ArrayList<>();

    @Override
    public void sendMessage(Message message) {
        messages.add(message);
    }

    public boolean containsMessageWith(String subject, String bodyText) throws java.io.IOException, javax.mail.MessagingException {
        return messages.stream().anyMatch(message ->
                message.getSubject().contains(subject) &&
                        message.getContent().toString().contains(bodyText));
    }

    public boolean containsMessageTo(String to) {
        return messages.stream().anyMatch(message -> message.getRecipient().contains(to));
    }

}
