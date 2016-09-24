package com.tenPines.mailer;

import com.tenPines.model.Message;

import java.util.List;

public interface PostOffice {
    void sendMessage(Message message);

    void changePostMan(PostMan postMan);

    List<UnsentMessage> getFailedMessages();
}
