package com.tenPines.application;


import com.tenPines.application.clock.Clock;
import com.tenPines.application.service.*;
import com.tenPines.mailer.UnsentMessage;
import com.tenPines.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SystemPalFacade {


    @Autowired
    FriendRelationService friendRelationService;

    @Autowired
    WorkerService workerService;

    @Autowired
    GiftDefaultService giftDefaultService;

    @Autowired
    WishlistService wishlistService;

    @Autowired
    MailerService mailerService;

    private Long reminderDayPeriod;
    private Clock clock;

    public Worker retrieveAssignedFriendFor(Long idParticipant) {
        Worker participant = workerService.retriveWorker(idParticipant);
        return friendRelationService.retrieveAssignedFriendFor(participant);
    }

    public FriendRelation createRelation(Worker giftGiver, Worker giftReceiver) {
        return friendRelationService.create(giftGiver,giftReceiver);
    }

    public void deleteRelation(Long from, Long to) {

        friendRelationService.deleteRelationByReceipt(retrieveAWorker(to));

    }

    public List<DefaultGift> retrieveAllGiftsDefaults() {
        List<DefaultGift> defaultGifts = giftDefaultService.getAll();
        if(defaultGifts.isEmpty()) {

            defaultGifts.add(DefaultGift.createGiftDfault("Nada","$0"));
        }
        return defaultGifts;
    }

    public void addGiftDefaults(DefaultGift defaultGift) {
        giftDefaultService.addGift(defaultGift);

    }

    public List<Wish> retrieveAllWishes() {
        return wishlistService.retrieveAllWishes();
    }

    public Worker retrieveAWorker(Long workerID) {
        return workerService.retriveWorker(workerID);
    }

    public List<Wish> retrievallWishesForWorker(Worker worker) {
        return wishlistService.retrieveByWorker(worker);
    }


    public Wish saveWish(Wish wish) {

        return wishlistService.saveWish(wish);
    }

    public Wish retrieveAWish(Long id) {
        return wishlistService.retrieveAWish(id);
    }

    public void updateWish(Wish wish) {
        wishlistService.updateWish(wish);
    }

    public void deleteAWish(Wish wish) {
        wishlistService.deleteAWish(wish);
    }

    public Worker retrieveWorkerByEmail(String email) {
        return workerService.retrieveWorkerByEmail(email);

    }
    public SystemPalFacade() {
        setReminderDayPeriod(7L);
    }

    public Long getReminderDayPeriod() {
        return reminderDayPeriod;
    }

    public void setReminderDayPeriod(Long reminderDayPeriod) {
        this.reminderDayPeriod = reminderDayPeriod;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public Clock getClock() {
        return clock;
    }

    public void deleteAWorker(Worker worker) {
        workerService.remove(worker);
    }

    public void changeIntention(Worker aWorker) {
        workerService.changeIntention(aWorker);
    }

    public List<Worker> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    public DefaultGift retrieveTheLastDefaultGift() {
        return retrieveAllGiftsDefaults().get(0);

    }

    public void deleteAllRelations() {
        friendRelationService.friendRelationRepository.deleteAllRelations();
    }

    public List<Worker> getPosibleFriendsTo(Long id) {
        Worker workerTo = workerService.retriveWorker(id);
        return friendRelationService.getAvailablesRelationsTo(workerTo);
    }

    public void editWorker(Worker workerEdited) throws Exception {
        workerService.save(workerEdited);
    }

    public void resendMessageFailure(UnsentMessage unsentMessage) {

        mailerService.resendMessageFailure(unsentMessage);
    }

    public List<FriendRelation> initializeRelations() {
        return friendRelationService.autoAssignRelations();
    }

    public List<FriendRelation> retrieveAllRelations() {
        return friendRelationService.getAllRelations();
    }
}
