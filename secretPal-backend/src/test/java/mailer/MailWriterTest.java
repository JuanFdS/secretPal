package mailer;

import builder.PersonBuilder;
import model.Person;
import org.junit.Before;
import org.junit.Test;
import javax.mail.Message;
import javax.mail.MessagingException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MailWriterTest {

    private Person aPal;
    private Message anAssignationMessage;


    @Before
    public void setUp() throws IOException, MessagingException {
        MailWriter aMailWriter = new MailWriter();
        aPal = new PersonBuilder().build();
        anAssignationMessage = aMailWriter.buildAssignationMessage(aPal);
    }

    @Test
    public void When_I_Build_An_Assignation_Message_The_Subject_Is_About_An_Assignation() throws MessagingException, IOException {
        assertEquals("[SecretPal] Se te ha asignado un amigo invisible", anAssignationMessage.getSubject());
    }

    @Test
    public void When_I_Build_An_Assignation_Message_The_Body_Tells_My_Pal_Information() throws IOException, MessagingException {
        assertEquals(assignationTextFor(aPal), anAssignationMessage.getContent());
    }

    private String assignationTextFor(Person receiver) {
        return "You're the secret pal of " + receiver.getName() +
                ", " + receiver.getLastName() + " The birthday is on: "
                + receiver.getBirthdayDate().toString();
    }
}
