package com.tenPines.application;

import com.tenPines.application.clock.Clock;
import com.tenPines.mailer.PostOffice;
import com.tenPines.model.*;
import com.tenPines.persistence.Repo;
import com.tenPines.persistence.SecretPalEventMethods;
import com.tenPines.utils.PropertyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Deprecated

@Service
public class SecretPalSystem {


    private Long reminderDayPeriod;
    private Repo<Worker> workerRepository;
    private SecretPalEventMethods secretPalEventRepository;
    private Repo<Wish> wishRepository;

    private Repo<Message> failedMails;

    private Clock clock;

    @Autowired
    private PostOffice postOffice;

    public Repo<Message> getFailedMails() {
        return failedMails;
    }

    public void setFailedMails(Repo<Message> failedMails) {
        this.failedMails = failedMails;
    }

    public SecretPalSystem() {
        setReminderDayPeriod(7L);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public Clock getClock() {
        return clock;
    }

    public void setWishRepository(Repo<Wish> wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void setWorkerRepository(Repo<Worker> workerRepository) {
        this.workerRepository = workerRepository;
    }

    public void setSecretPalEventRepository(SecretPalEventMethods eventRepository){
        this.secretPalEventRepository = eventRepository;
    }

    public SecretPalEvent saveEvent(SecretPalEvent newEvent) {
        return this.secretPalEventRepository.save(newEvent);
    }

    public List<Worker> retrieveAllWorkers() {
        return workerRepository.retrieveAll();
    }

    public Worker retrieveAWorker(Long id) {
        return workerRepository.findById(id);
    }

    public void deleteAWorker(Worker aWorker) {
        workerRepository.delete(aWorker);
    }

    public void changeIntention(Worker aWorker) {
        Worker worker = retrieveAWorker(aWorker.getId());
        worker.changeParticipationIntention();
        workerRepository.update(worker);
    }

    public Worker getWorker(Worker aWorker) {
        return workerRepository.refresh(aWorker);
    }

    public List<Wish> retrieveAllWishes() {
        return wishRepository.retrieveAll();
    }

    public Wish saveWish(Wish newWish) {
        return wishRepository.save(newWish);
    }

    public Wish retrieveAWish(Long id) {
        return wishRepository.findById(id);
    }

    public void deleteAWish(Wish wish) {
        wishRepository.delete(wish);
    }

    public void updateWish(Wish wish) {
        wishRepository.update(wish);
    }

    public List<Worker> retrieveParticipants() {
       return workerRepository.retrieveByCondition("wantsToParticipate", true);
    }

    public Worker retrieveAssignedFriendFor(Worker participant) {
        return secretPalEventRepository.retrieveAssignedFriendFor(participant);
    }

    public FriendRelation createRelationInEvent(SecretPalEvent event, Worker giftGiver, Worker giftReceiver) throws IOException, MessagingException {
        FriendRelation friendRelation = secretPalEventRepository.createRelationInEvent(event, giftGiver, giftReceiver);
        Message message = friendRelation.createMessage();
        //postMan.sendMessage(unSentMessage);
        return friendRelation;
    }

    public void deleteRelationInEvent(FriendRelation friendRelation) {
        secretPalEventRepository.deleteRelationInEvent(friendRelation);
    }

    public SecretPalEvent retrieveEvent(SecretPalEvent event) {
        return secretPalEventRepository.refresh(event);
    }

    public FriendRelation retrieveRelation(Long from, Long to) {
      return secretPalEventRepository.retrieveRelation(from, to);
    }

    public Worker retrieveWorkerByEmail(String workerEmail) {
        return workerRepository.retrieveByCondition("eMail", workerEmail).stream().findFirst().orElseThrow(
                () -> new RuntimeException("The user does not exist")
        );
    }

    public Long getReminderDayPeriod() {
        return reminderDayPeriod;
    }

    public void setReminderDayPeriod(Long reminderDayPeriod) {
        this.reminderDayPeriod = reminderDayPeriod;
    }

    public List<Wish> retrievallWishesForWorker(Worker worker) {
        return wishRepository.retrieveByCondition("worker", worker);
    }

    public SecretPalEvent retrieveCurrentEvent() {
        return secretPalEventRepository.retrieveEvent();
    }


    public void deleteAllRelationsInEvent(SecretPalEvent event) {
        List<FriendRelation> friendRelations = secretPalEventRepository.retrieveAllRelations();
        for( FriendRelation friendRelation : friendRelations) {
            secretPalEventRepository.deleteRelationInEvent(friendRelation);
        }
    }
}
