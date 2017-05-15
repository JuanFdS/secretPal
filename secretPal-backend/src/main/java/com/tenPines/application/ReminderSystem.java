package com.tenPines.application;

import com.tenPines.application.clock.Clock;
import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.MailerService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.HappyBithdayMessageBuilder;
import com.tenPines.builder.ReminderAproachTheBirthdayBuilder;
import com.tenPines.mailer.PostOffice;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.RelationEstablisher;
import com.tenPines.model.RelationsScavenger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ReminderSystem {
    private final Clock clock;
    private final RelationEstablisher relationEstablisher;
    private final FriendRelationService friendRelationService;
    private final RelationsScavenger relationsScavenger;
    private final PostOffice postOffice;
    private final MailerService mailerService;
    private final WorkerService workerService;

    public ReminderSystem(Clock clock, RelationEstablisher relationEstablisher, FriendRelationService friendRelationService, RelationsScavenger relationsScavenger, PostOffice postOffice, MailerService mailerService, WorkerService workerService) {
        this.clock = clock;
        this.relationEstablisher = relationEstablisher;
        this.friendRelationService = friendRelationService;
        this.relationsScavenger = relationsScavenger;
        this.postOffice = postOffice;
        this.mailerService = mailerService;
        this.workerService = workerService;
    }


    @Scheduled(fixedDelay = 86400000) //86400000 = 1 dia
    public void findNewBirthdaysAndAssignThem() {
        LocalDate targetBirthDate = clock.now().plus(Period.ofMonths(1));
        relationsScavenger.findWorkersWithUnassignedBirthdayOn(
                targetBirthDate)
                .forEach(birthdayWorker -> {
                    FriendRelation newRelation = relationEstablisher.buildRelationFor(birthdayWorker, targetBirthDate.getYear());
                    newRelation = friendRelationService.save(newRelation);
                    postOffice.sendMessage(
                            new ReminderAproachTheBirthdayBuilder().buildMessage(newRelation));
                });


    }


    @Scheduled(fixedDelay = 86400000)
    public void sendHappyBithdayMessages() {
        workerService.findWorkersWithBirthday(clock.now())
                .forEach(worker -> postOffice.sendMessage(new HappyBithdayMessageBuilder().buildMesage(worker)));

    }


}
