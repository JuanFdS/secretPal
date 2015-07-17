package mailer;

import model.Person;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

public interface PostMan {

    void notifyPersonWithSecretPalInformation(Person participant, Person secretPal) throws MessagingException, IOException;


}
