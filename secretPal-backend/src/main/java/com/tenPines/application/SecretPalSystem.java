package com.tenPines.application;

import com.tenPines.mailer.PostOffice;
import com.tenPines.mailer.SMTPPostMan;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Wish;
import com.tenPines.model.Worker;
import com.tenPines.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;

public class SecretPalSystem {


    private AbstractRepository<Worker> workerRepository;
    private SecretPalEventMethods secretPalEventRepository;
    private AbstractRepository<Wish> wishRepository;


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
        SMTPPostMan postMan = new PostOffice().callThePostMan();
        postMan.notifyPersonWithSecretPalInformation(giftGiver, giftReceiver);
    }
}
