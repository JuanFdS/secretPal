package com.tenPines.application;

import com.tenPines.mailer.PostMan;
import com.tenPines.mailer.PostOffice;
import com.tenPines.model.*;
import com.tenPines.persistence.AbstractRepository;
import com.tenPines.persistence.SecretPalEventMethods;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;

public class SecretPalSystem {


    Long reminderDayPeriod;
    private AbstractRepository<Worker> workerRepository;
    private SecretPalEventMethods secretPalEventRepository;
    private AbstractRepository<Wish> wishRepository;
    private PostMan postMan;
    private Clock clock;

    public SecretPalSystem() throws IOException {
        postMan = new PostOffice().callThePostMan(); //TODO Hacer esto un bean y pasarlo por Spring
        setReminderDayPeriod(7L);
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public void setWishRepository(AbstractRepository<Wish> wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void setWorkerRepository(AbstractRepository<Worker> workerRepository) {
        this.workerRepository = workerRepository;
    }

    public void setSecretPalEventRepository(SecretPalEventMethods eventRepository){
        this.secretPalEventRepository = eventRepository;
    }

    public Worker saveWorker(Worker newWorker) {
        return this.workerRepository.save(newWorker);
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

    public void createRelationInEvent(SecretPalEvent event, Worker giftGiver, Worker giftReceiver) {
        secretPalEventRepository.createRelationInEvent(event, giftGiver, giftReceiver);
    }

    public SecretPalEvent retrieveEvent() {
        return secretPalEventRepository.retrieveEvent();
    }

    public FriendRelation retrieveRelation(Long from, Long to) {
      return secretPalEventRepository.retrieveRelation(from, to);

    }

    public void deleteRelationInEvent(SecretPalEvent event, FriendRelation friendRelation) {
     secretPalEventRepository.deleteRelationInEvent(event, friendRelation);
    }

    public void notifySender(Worker giftGiver, Worker giftReceiver) throws IOException, MessagingException {
        postMan.notifyPersonWithSecretPalInformation(giftGiver, giftReceiver);
    }

    public Long getReminderDayPeriod() {
        return reminderDayPeriod;
    }

    public void setReminderDayPeriod(Long reminderDayPeriod) {
        this.reminderDayPeriod = reminderDayPeriod;
    }

    public void sendReminders() throws IOException, MessagingException {
        for (FriendRelation friendRelation : secretPalEventRepository.retrieveAllRelations()) {

            LocalDate today = clock.now();

            MonthDay birthday = MonthDay.from(friendRelation.getGiftReceiver().getDateOfBirth());
            MonthDay todaysDate = MonthDay.from(today.plusDays(getReminderDayPeriod()));

            if (birthday.equals(todaysDate)) {
                postMan.notifyPersonWithSecretPalInformation(
                        friendRelation.getGiftGiver(),
                        friendRelation.getGiftReceiver());
            }
        }
    }

    public PostMan getPostMan() {
        return postMan;
    }

    public void setPostMan(PostMan postMan) {
        this.postMan = postMan;
    }


}
