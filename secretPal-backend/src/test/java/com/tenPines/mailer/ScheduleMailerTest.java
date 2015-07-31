package com.tenPines.mailer;


import com.tenPines.application.SecretPalSystem;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.FakeClock;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class ScheduleMailerTest {
    Worker friendWorker;
    Worker birthdayWorker;
    DumbPostMan dumbPostMan;
    private SecretPalSystem secretPalSystem;
    @Autowired
    private WebApplicationContext webApplicationContext;

    private void setUp(LocalDate today, LocalDate birthday) throws Exception {
        secretPalSystem = (SecretPalSystem) webApplicationContext.getBean("secretPalSystem");

        secretPalSystem.setClock(new FakeClock(today));

        dumbPostMan = new DumbPostMan();
        secretPalSystem.setSafePostMan(dumbPostMan);


        friendWorker = secretPalSystem.saveWorker(new WorkerBuilder().build());
        birthdayWorker = secretPalSystem.saveWorker(new WorkerBuilder().withBirthDayDate(birthday).build());

        secretPalSystem.changeIntention(friendWorker);
        secretPalSystem.changeIntention(birthdayWorker);

        SecretPalEvent event = secretPalSystem.retrieveEvent();

        secretPalSystem.createRelationInEvent(event, friendWorker, birthdayWorker);
        dumbPostMan.flushSentMails(); //Ya crea un mail al crear la relacion.
    }

    @Test
    public void When_A_Worker_Has_A_Friends_Birthday_A_Week_From_Now_The_System_Should_Mail_Him() throws Exception {
        setUp(LocalDate.of(2000, Month.APRIL, 3), LocalDate.of(1900, Month.APRIL, 10));

        secretPalSystem.sendReminders();

        assertThat(dumbPostMan.containsMessageTo(friendWorker.geteMail()), is(true));
    }

    @Test
    public void When_A_Worker_Has_A_Friends_Birthday_A_Day_From_Now_And_It_Is_Configured_For_a_day_before_The_System_Should_Mail_Him() throws Exception {
        setUp(LocalDate.of(2000, Month.APRIL, 9), LocalDate.of(1900, Month.APRIL, 10));

        secretPalSystem.setReminderDayPeriod(1L);

        secretPalSystem.sendReminders();

        assertThat(dumbPostMan.containsMessageTo(friendWorker.geteMail()), is(true));
    }

    @Test
    public void When_A_Worker_Has_A_Friends_Birthday_After_Today_No_Mail_Should_be_Sent() throws Exception {
        setUp(LocalDate.of(2000, Month.APRIL, 9), LocalDate.of(1900, Month.JANUARY, 10));

        secretPalSystem.setReminderDayPeriod(1L);

        secretPalSystem.sendReminders();

        assertThat(dumbPostMan.containsMessageTo(friendWorker.geteMail()), is(false));
    }
}