package com.tenPines.mailer;

import com.tenPines.model.Worker;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public interface PostMan {

    //TODO Un postman sabe mucho más del domio de lo que me siento comodo
    void notifyPersonWithSecretPalInformation(Worker participant, Worker secretPal) throws MessagingException, IOException;

    void sendMessage(Message message) throws MessagingException;

}
