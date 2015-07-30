package com.tenPines.mailer;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.FakeClock;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import com.tenPines.persistence.AbstractRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class SMTPPostManTest {

    AbstractRepository<Message> failedMails;
    SecretPalSystem secretPalSystem;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private Worker friendWorker;
    private Worker birthdayWorker;

    private UnsafePostMan failingPostMan;
    private FailProofPostMan failProofPostMan;

    @Before
    public void setUp() throws Exception {
        LocalDate today = LocalDate.of(2000, Month.FEBRUARY, 20);
        LocalDate birthday = today.plusDays(7L);

        secretPalSystem = (SecretPalSystem) webApplicationContext.getBean("secretPalSystem");
        failedMails = (AbstractRepository<Message>) webApplicationContext.getBean("failedMails");
        failProofPostMan = (FailProofPostMan) webApplicationContext.getBean("failProofPostMan");

        secretPalSystem.setClock(new FakeClock(today));

        friendWorker = secretPalSystem.saveWorker(new WorkerBuilder().build());
        birthdayWorker = secretPalSystem.saveWorker(new WorkerBuilder().withBirthDayDate(birthday).build());

        secretPalSystem.changeIntention(friendWorker);
        secretPalSystem.changeIntention(birthdayWorker);

        failingPostMan = message -> {
            throw new MessagingException("From the test, with love <3");
        };
        failProofPostMan.setPostMan(failingPostMan);
    }


    @Test
    public void When_A_Mail_Fails_It_Should_Be_Stored_For_Future_resend() throws IOException, MessagingException {

        SecretPalEvent event = secretPalSystem.retrieveEvent();
        secretPalSystem.createRelationInEvent(event, friendWorker, birthdayWorker);

        assertThat(failedMails.retrieveAll(), hasSize(1));
        assertThat(failedMails.retrieveAll(), contains(hasProperty("recipient", is(friendWorker.geteMail()))));
    }

    @Test
    public void If_there_are_mails_to_be_sent_then_send_The() throws IOException, MessagingException {
        SecretPalEvent event = secretPalSystem.retrieveEvent();
        secretPalSystem.createRelationInEvent(event, friendWorker, birthdayWorker);

        //The message has failed.

        failProofPostMan.setPostMan(Mockito.mock(UnsafePostMan.class));

        failProofPostMan.resendFailedMessages();

        assertThat(failedMails.retrieveAll(), hasSize(0));
    }

}