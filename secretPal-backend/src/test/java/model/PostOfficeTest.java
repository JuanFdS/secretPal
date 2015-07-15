package model;

import builder.PersonBuilder;
import com.github.javafaker.Faker;
import mailer.DumbPostMan;
import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;

import java.io.IOException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class PostOfficeTest {

    private PostOffice aPostOffice;
    private DumbPostMan dumbPostMan;
    private Person aPal;

    @Before
    public void setUp(){
        dumbPostMan = new DumbPostMan();
        aPostOffice = new PostOffice(dumbPostMan);
        aPal = new PersonBuilder().build();
    }

    @Test
    public void When_I_try_to_send_a_mail_to_an_invalid_address_an_exception_is_raised(){
        try {
            aPostOffice.sendAssignationMailToWithFriendInformation("fakeEmail", aPal);
            fail("An exception should have been raised");
        } catch(RuntimeException e) {
            assertEquals("The recipient email address is invalid", e.getMessage());
            }
    }

    @Test
    public void When_I_try_to_send_a_mail_with_a_valid_address_the_operation_is_successful(){
        Faker faker = new Faker();
        aPostOffice.sendAssignationMailToWithFriendInformation(faker.internet().emailAddress(), aPal);
        validateThatAssignationMainArrivesProperly();

    }

    private void validateThatAssignationMainArrivesProperly() {
        assertEquals(1, dumbPostMan.getEmailCount());
        Message message = dumbPostMan.getMessage();
        try {
            assertEquals(assignationSubject(), message.getSubject());
            assertEquals(assignationTextFor(aPal), message.getContent());
        } catch (IOException e) {
            messageWentWrong();
        } catch (MessagingException e) {
            messageWentWrong();
        }
    }

    private void messageWentWrong(){
        fail("There's something wrong with this message");
    }

    private String assignationSubject() {
        return "[SecretPal] A secret pal was assigned to you!";
    }

    private String assignationTextFor(Person receiver) {
        return "You're the secret pal of " + receiver.getName() +
                ", " + receiver.getLastName() + " The birthday is on: "
                + receiver.getBirthdayDate().toString();
    }

}
