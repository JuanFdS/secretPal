package model;

import builder.PersonBuilder;
import mailer.DumbPostMan;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Before;
import org.junit.Test;
import utils.PropertyParser;

import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertTrue;


public class PostManTest {

    private DumbPostMan aPostMan;
    private Person aSecretPal;

    @Before
    public void setUp() throws IOException, ConfigurationException {
        Properties templateProperties = new PropertyParser();
        templateProperties.load(new FileInputStream("src/main/resources/mailTemplate.properties"));
        aPostMan = new DumbPostMan(templateProperties);
        aSecretPal = new PersonBuilder().build();
    }

    @Test
    public void When_I_try_to_send_a_mail_with_a_valid_address_the_operation_is_successful() throws MessagingException, IOException {
        Person aReceiver = new PersonBuilder().withEmail("roman.rizzi@10pines.com").build();
        aPostMan.notifyPersonWithSecretPalInformation(aReceiver, aSecretPal);
        validateThatAssignationMainArrivesProperly();
    }

    private void validateThatAssignationMainArrivesProperly() throws IOException, MessagingException {
        assertTrue(aPostMan.containsMessageWith(assignationSubject(), assignationTextFor(aSecretPal)));
    }

    private String assignationSubject() {
        return "[SecretPal] A secret pal was assigned to you!";
    }

    private String assignationTextFor(Person receiver) {
        return "You're the secret pal of " + receiver.getFullName() + ". His/Her birthday is on: "
                + receiver.getBirthdayDate().toString();
    }

}
