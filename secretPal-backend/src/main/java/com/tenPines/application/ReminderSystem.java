package com.tenPines.application;

import com.tenPines.application.clock.Clock;
import com.tenPines.application.clock.ClockConfig;
import com.tenPines.application.clock.FakeClock;
import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.FriendRelationMessageBuilder;
import com.tenPines.builder.HappyBithdayMessageBuilder;
import com.tenPines.builder.ReminderAproachTheBirthdayBuilder;
import com.tenPines.mailer.PostOffice;
import com.tenPines.model.Worker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.stream.Stream;

@Service
public class ReminderSystem {

    private static Logger logger = Logger.getLogger("reminder");
    @Autowired
    private FriendRelationService friendRelationService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private Clock clock;

    @Autowired
    private SecretPalProperties secretPalProperties;
    @Autowired
    private PostOffice postOffice;

    @Scheduled(fixedDelay = 86400000) //86400000 = 1 dia
    public void sendRemindersTheLastBirthday() {
        logger.info("Send mails for forgetful gifters.");

        friendRelationService.getAllRelations().stream()
                .filter(friendRelation ->
                        MonthDay.from(friendRelation.getGiftReceiver().getDateOfBirth())
                                .equals(
                                        MonthDay.from(clock.now().plusDays(secretPalProperties.getReminderDayPeriod())))
                )
                .forEach(friendRelation ->
                        postOffice.sendMessage(
                                new ReminderAproachTheBirthdayBuilder().buildMessage(friendRelation)
                        ));
    }


    @Scheduled(fixedDelay = 86400000) //86400000 = 1 dia
    public void sendAssignedRelation() {
        logger.info("Assigned friendRelation.");

        friendRelationService.getAllRelations().forEach(friendRelation ->
                postOffice.sendMessage(
                        new FriendRelationMessageBuilder().buildMessage(friendRelation)
                ));
    }


    @Scheduled(fixedDelay = 86400000)
    public void sendHappyBithdayMessages(){
        logger.info("Sending happy bithday mails");

        workerService.getAllParticipants().stream()
                .filter( worker ->
                        MonthDay.from(worker.getDateOfBirth())
                            .equals(
                                    MonthDay.from(clock.now()))
                )
                .forEach(worker -> postOffice.sendMessage(new HappyBithdayMessageBuilder().buildMesage(worker)));

    }


}
