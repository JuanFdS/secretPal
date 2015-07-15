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

    public void sendAssignationMailToWithFriendInformation(String emailAddress, Person pal)  {
        checkIfValidEmail(emailAddress);
        try {
            mailService.sendMessage(mailWriter.buildAssignationMessage(pal));
        } catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void checkIfValidEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new RuntimeException("The recipient email address is invalid");
    }

}
