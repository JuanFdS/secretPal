package com.tenPines.mailer;

import com.tenPines.model.Message;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPostMan implements PostMan {

    private static List<Message> messages = new ArrayList<>();

    void flushSentMails() {
        messages.clear();
    }

    @Override
    public void sendMessage(Message message) {
        messages.add(message);
    }

    boolean containsMessageTo(String to) {
        return messages.stream().anyMatch(message -> message.getRecipient().contains(to));
    }

}
