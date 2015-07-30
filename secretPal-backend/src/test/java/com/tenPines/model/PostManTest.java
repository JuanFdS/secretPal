package com.tenPines.model;

import com.tenPines.builder.WorkerBuilder;
import com.tenPines.mailer.DumbPostMan;
import com.tenPines.utils.PropertyParser;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertTrue;


public class PostManTest {

    private DumbPostMan aPostMan;
    private Worker aSecretPal;

    @Before
    public void setUp() throws Exception {
        Properties templateProperties = new PropertyParser("src/main/resources/mailTemplate.properties");
        aPostMan = new DumbPostMan(templateProperties);
        aSecretPal = new WorkerBuilder().build();
    }

    @Test
    public void When_I_try_to_send_a_mail_with_a_valid_address_the_operation_is_successful() throws Exception {
        Worker aReceiver = new WorkerBuilder().withEmail("roman.rizzi@10pines.com").build();
        aPostMan.notifyPersonWithSecretPalInformation(aReceiver, aSecretPal);
        validateThatAssignationMainArrivesProperly();
    }

    private void validateThatAssignationMainArrivesProperly() throws IOException, MessagingException {
        assertTrue(aPostMan.containsMessageWith(assignationSubject(), assignationTextFor(aSecretPal)));
    }

    private String assignationSubject() {
        return "[SecretPal] A secret pal was assigned to you!";
    }

    private String assignationTextFor(Worker receiver) {
        return "You're the secret pal of " + receiver.getFullName() + ". His/Her birthday is on: "
                + receiver.getDateOfBirth().toString();
    }

}
