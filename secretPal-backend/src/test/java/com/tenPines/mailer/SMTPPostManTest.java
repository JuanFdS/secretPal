package com.tenPines.mailer;

import com.github.javafaker.Faker;
import com.tenPines.integration.SpringBaseTest;
import com.tenPines.model.Message;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import javax.mail.MessagingException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SMTPPostManTest extends SpringBaseTest {

    @Autowired
    private PostOffice postOffice;

    private Message aMessage;
    private Faker faker = new Faker();
    private String anEmail;

    @Before
    public void setUp() {
        anEmail = faker.internet().emailAddress();
        aMessage = Message.create(anEmail,
                                  faker.lorem().sentence(),
                                  faker.lorem().paragraph() );
    }

    @Test
    @DirtiesContext
    public void When_A_Mail_Fails_It_Should_Be_Stored_For_Future_resend() {
        setupAFailingPostman();

        postOffice.sendMessage(aMessage);

        assertThat(postOffice.getFailedMessages(), hasSize(1));
        assertThat(postOffice.getFailedMessages(), contains(hasProperty("recipient", is(anEmail))));
    }

    private void setupAFailingPostman() {
        PostMan failingPostMan = message -> {
            throw new UnableToSendMessage(new MessagingException("Upsis"));
        };
        postOffice.changePostMan(failingPostMan);
    }
}