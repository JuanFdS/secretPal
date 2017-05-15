package com.tenPines.mailer;


import com.tenPines.application.ReminderSystem;
import com.tenPines.application.SecretPalProperties;
import com.tenPines.application.clock.FakeClock;
import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.integration.SpringBaseTest;
import com.tenPines.model.Worker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ScheduleMailerTest extends SpringBaseTest {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private ReminderSystem reminderSystem;
    @Autowired
    private FakeClock clock;
    @Autowired
    private InMemoryPostMan postMan;
    @Autowired
    private SecretPalProperties secretPalProperties;
    @Autowired
    private FriendRelationService friendRelationService;


    private Worker friendWorker;
    private Worker birthdayWorker;

    @Test
    public void When_A_Worker_Has_A_Friends_Birthday_A_Week_From_Now_The_System_Should_Mail_Him(){
        clock.setTime(LocalDate.of(2015, Month.AUGUST, 10));

        friendWorker = workerService.save(new WorkerBuilder().build());
        birthdayWorker = workerService.save(new WorkerBuilder().withBirthDayDate(LocalDate.of(1994, Month.SEPTEMBER, 10)).build());

        reminderSystem.findNewBirthdaysAndAssignThem();

        assertThat(postMan.messagesTo(friendWorker.getMail()), hasSize(1));
        assertThat(postMan.messagesTo(friendWorker.getMail()), contains(hasProperty("body",
                allOf(
                        containsString(birthdayWorker.getFullName())
                        ))));
    }

    @Test
    public void When_A_Worker_Has_A_Friends_Birthday_After_Today_No_Mail_Should_be_Sent() {
        clock.setTime(LocalDate.of(2000, Month.APRIL, 9));

        friendWorker = workerService.save(new WorkerBuilder().build());
        birthdayWorker = workerService.save(new WorkerBuilder().withBirthDayDate(LocalDate.of(1900, Month.JANUARY, 10)).build());

        reminderSystem.findNewBirthdaysAndAssignThem();

        assertThat(postMan.messagesTo(friendWorker.getMail()), empty());
    }
}