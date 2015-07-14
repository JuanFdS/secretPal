package model;


import mailer.GmailPostMan;
import mailer.MailWriter;
import mailer.PostManService;
import org.apache.commons.validator.routines.EmailValidator;


import javax.mail.MessagingException;
import java.io.IOException;


public class PostOffice {

    private PostManService mailService;
    private MailWriter mailWriter = new MailWriter();

    public PostOffice(PostManService mailService) {
        this.mailService = mailService;
    }

    public void sendMailToWithFriendName(String emailAddress, Person pal) throws IOException, MessagingException {
        checkIfValidEmail(emailAddress);
        mailService.sendMessage(mailWriter.buildAssignationMessage(pal));
    }

    private void checkIfValidEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new RuntimeException("The recipient email address is invalid");
    }

}
