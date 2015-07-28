package com.tenPines.mailer;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.FakeClock;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import com.tenPines.persistence.DatabaseFailedMails;
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

import static org.mockito.Matchers.any;
import static org.springframework.test.util.AssertionErrors.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class SMTPPostManTest {

    DatabaseFailedMails failedMails;
    SecretPalSystem secretPalSystem;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private Worker friendWorker;
    private Worker birthdayWorker;

    private PostMan postManMock;

    @Before
    public void setUp() throws Exception {
        LocalDate today = LocalDate.of(2000, Month.FEBRUARY, 20);
        LocalDate birthday = today.plusDays(7L);

        secretPalSystem = (SecretPalSystem) webApplicationContext.getBean("secretPalSystem");

        secretPalSystem.setClock(new FakeClock(today));

        //TODO: No hay forma de verificar esto desde el systema, porque el PostMan no se puede testear; entonces si o si necesito un dumb
        postManMock = Mockito.mock(PostMan.class);

        friendWorker = secretPalSystem.saveWorker(new WorkerBuilder().build());
        birthdayWorker = secretPalSystem.saveWorker(new WorkerBuilder().withBirthDayDate(birthday).build());

        secretPalSystem.changeIntention(friendWorker);
        secretPalSystem.changeIntention(birthdayWorker);

        SecretPalEvent event = secretPalSystem.retrieveEvent();

        secretPalSystem.createRelationInEvent(event, friendWorker, birthdayWorker);
    }


    @Test
    public void When_A_Mail_Fails_It_Should_Be_Stored_For_Future_resend() throws IOException, MessagingException {
        Mockito.doThrow(new MessagingException("From test :D")).doNothing().when(postManMock).sendMessage(any(Message.class));

        //TODO: El notificar esta por fuera de la asignacion entonces es un metodo aparte. Todo esto se puede encapsular en la relacion
        try {
            secretPalSystem.notifySender(friendWorker, birthdayWorker);
            fail("This should have failed");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}