package com.tenPines.mailer;

import com.tenPines.model.Worker;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public interface PostMan {

    void notifyPersonWithSecretPalInformation(Worker participant, Worker secretPal) throws MessagingException, IOException;


}
