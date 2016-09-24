package com.tenPines.application;

import com.tenPines.application.clock.Clock;
import com.tenPines.application.service.FriendRelationService;
import com.tenPines.builder.FriendRelationMessageBuilder;
import com.tenPines.mailer.PostOffice;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.MonthDay;

@Service
public class ReminderSystem {
    private static Logger logger = Logger.getLogger("reminder");
    @Autowired
    private FriendRelationService friendRelationService;
    @Autowired
    private Clock clock;
    @Autowired
    private SecretPalProperties secretPalProperties;
    @Autowired
    private PostOffice postOffice;

    @Scheduled(fixedDelay = 86400000) //86400000 = 1 dia
    public void sendReminders() {
        logger.info("Send mails for forgetful gifters.");

        friendRelationService.getAllRelations().stream()
                .filter(friendRelation ->
                        MonthDay.from(friendRelation.getGiftReceiver().getDateOfBirth())
                                .equals(
                                        MonthDay.from(clock.now().plusDays(secretPalProperties.getReminderDayPeriod())))
                )
                .forEach(friendRelation ->
                        postOffice.sendMessage(
                                new FriendRelationMessageBuilder().buildMessage(friendRelation)
                        ));
    }
}
