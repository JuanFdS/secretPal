package com.tenPines.mailer;

import com.tenPines.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryPostMan implements PostMan {

    public static List<Message> messages = new ArrayList<>();

    void flushSentMails() {
        messages.clear();
    }

    @Override
    public void sendMessage(Message message) {
        messages.add(message);
    }

    List<Message> messagesTo(String to) {
        return messages.stream().filter(message -> message.getRecipient().contains(to)).collect(Collectors.toList());
    }

}
