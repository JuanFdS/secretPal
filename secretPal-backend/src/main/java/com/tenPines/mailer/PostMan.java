package com.tenPines.mailer;

import model.Person;

import javax.mail.MessagingException;
import java.io.IOException;

public interface PostMan {

    void notifyPersonWithSecretPalInformation(Person participant, Person secretPal) throws MessagingException, IOException;


}
