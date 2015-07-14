package model;


import builder.PersonBuilder;
import com.github.javafaker.Faker;
import mailer.DumbPostMan;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class PostOfficeTest {

    private PostOffice aPostOffice;

    @Before
    public void setUp(){
        aPostOffice = new PostOffice( new DumbPostMan());
    }

    @Test
    public void When_I_try_to_send_a_mail_to_an_invalid_address_an_exception_is_raised() throws IOException, MessagingException, GeneralSecurityException {
        Person aPal = new PersonBuilder().build();
        try {
            aPostOffice.sendMailToWithFriendName("fakeEmail", aPal);
            fail("An exception should have been raised");
        } catch(RuntimeException e) {
            assertEquals("The recipient email address is invalid", e.getMessage());
            }
    }

    @Test
    public void When_I_try_to_send_a_mail_with_a_valid_address_the_operation_is_successful() throws IOException, MessagingException, GeneralSecurityException {
        Faker faker = new Faker();
        Person aPal = new PersonBuilder().build();
        aPostOffice.sendMailToWithFriendName(faker.internet().emailAddress(), aPal);
    }

}
