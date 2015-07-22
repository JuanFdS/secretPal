package com.tenPines.application;

import com.tenPines.mailer.PostOffice;
import com.tenPines.mailer.SMTPPostMan;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import com.tenPines.persistence.*;

import javax.mail.MessagingException;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;


public class SecretPalSystem {

    private DatabasePersonDao personRepository = new DatabasePersonDao(HibernateUtils.createSessionFactory());
    private DatabaseSecretPalEventDao secretPalEventRepository = new DatabaseSecretPalEventDao(HibernateUtils.createSessionFactory());
    //private AbstractRepository<SecretPalEvent> friendRelationRepository = new DatabaseDao(HibernateUtils.createSessionFactory());


    public void savePerson(Worker newWorker) {
        this.personRepository.save(newWorker);
    }

    public List<Worker> retrieveAllPeople() {
        return personRepository.retrieveAll();
    }

    public Worker retrieveAPerson(Long id) {
        return personRepository.findById(id);
    }

    public void deletePerson(Worker aWorker) {
        personRepository.delete(aWorker);
    }

    public void changeIntention(Worker aWorker) {
        Worker worker = retrieveAPerson(aWorker.getId());
        worker.changeParticipationIntention();
        personRepository.update(worker);
    }

    public Worker getWorker(Worker aWorker) {
        return personRepository.refresh(aWorker);
    }

    public List<Worker> retrieveParticipants() {
       return personRepository.retrieveParticipants();
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
