package com.tenPines.mailer;

import com.tenPines.model.Message;

import javax.mail.MessagingException;

public interface UnsafePostMan {
    void sendMessage(Message message) throws MessagingException;
}
