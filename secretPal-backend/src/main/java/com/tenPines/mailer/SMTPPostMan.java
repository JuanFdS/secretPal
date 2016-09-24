package com.tenPines.mailer;

import com.tenPines.model.Message;

import javax.mail.MessagingException;
import javax.mail.Transport;

class SMTPPostMan implements PostMan {
    @Override
    public void sendMessage(Message message) {
        try {
            Transport.send(message.toJavax());
        } catch (MessagingException e) {
            throw new UnableToSendMessage(e);
        }
    }


}