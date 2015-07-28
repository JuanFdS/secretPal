package com.tenPines.mailer;

import com.tenPines.model.Message;

import javax.mail.MessagingException;
import javax.mail.Transport;

public class SMTPPostMan implements PostMan {
    @Override
    public void sendMessage(Message message) throws MessagingException {
        Transport.send(message.toJavax());
    }


}