package mailer;




import model.Person;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.Message;
import java.io.IOException;


public class MailWriter {

    private GmailPostMan postMan = new GmailPostMan();


    public Message buildAssignationMessage(Person receiver) throws MessagingException, IOException {
        return  createEmailFor(receiver.geteMail(), assignationSubject(), assignationBodyText(receiver));
    }

    private String assignationSubject(){
        return "[SecretPal] A secret pal was assigned to you!";
    }

    private String assignationBodyText(Person receiver) {
        return "You're the secret pal of " + receiver.getName() +
                ", " + receiver.getLastName() + " The birthday is on: "
                + receiver.getBirthdayDate().toString();
    }

    private Message createEmailFor(String to, String subject, String bodyText) throws MessagingException {
        Message message = postMan.createEmptyMessage();
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(bodyText);
        return message;
    }

}
