package com.tenPines.mailer;

import com.tenPines.model.Message;

public interface SafePostMan {
    void sendMessage(Message message);
}
