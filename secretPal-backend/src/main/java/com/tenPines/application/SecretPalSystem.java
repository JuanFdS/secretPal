package com.tenPines.application;

import com.tenPines.builder.FriendRelationMessageBuilder;
import com.tenPines.mailer.DumbPostMan;
import com.tenPines.mailer.SafePostMan;
import com.tenPines.model.*;
import com.tenPines.persistence.AbstractRepository;
import com.tenPines.persistence.SecretPalEventMethods;
import com.tenPines.utils.PropertyParser;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.MonthDay;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public class SecretPalSystem {

    protected static Logger logger = Logger.getLogger("service");
    public String mailTemplateProperties = "src/main/resources/mailTemplate.properties";

    Long reminderDayPeriod;
    private AbstractRepository<Worker> workerRepository;
    private SecretPalEventMethods secretPalEventRepository;
    private AbstractRepository<Wish> wishRepository;

    private  SafePostMan smtpPostMan;
    private SafePostMan dumbPostman = new DumbPostMan();

    private SafePostMan safePostMan;

    private AbstractRepository<Message> failedMails;
    private Clock clock;

    public AbstractRepository<Message> getFailedMails() {
        return failedMails;
    }

    public SafePostMan getSmtpPostMan() {
        return smtpPostMan;
    }

    public void setSmtpPostMan(SafePostMan smtpPostMan) {
        this.smtpPostMan = smtpPostMan;
    }

    public void setFailedMails(AbstractRepository<Message> failedMails) {
        this.failedMails = failedMails;
    }
    public SecretPalSystem() {
        setReminderDayPeriod(7L);
        setSafePostMan( smtpPostMan );
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
        safePostMan.sendMessage(message);
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

    @Scheduled(fixedDelay = 86400000) //86400000 = 1 dia
    public void sendReminders() throws IOException, MessagingException {
        logger.info("Send mails for forgetful gifters.");

        Stream<FriendRelation> friendRelationStream = secretPalEventRepository.retrieveAllRelations().stream();
        friendRelationStream.filter(friendRelation ->
                        MonthDay.from(friendRelation.getGiftReceiver().getDateOfBirth())
                                .equals(
                                        MonthDay.from(clock.now().plusDays(getReminderDayPeriod())))
        ).forEach(friendRelation -> safePostMan.sendMessage(
                new FriendRelationMessageBuilder().buildMessage(friendRelation)
        ));
    }

    public SafePostMan getSafePostMan() {
        return safePostMan;
    }

    public void setSafePostMan(SafePostMan safePostMan) {
        this.safePostMan = safePostMan;
    }

    public List<Wish> retrievallWishesForWorker(Worker worker) {
        return wishRepository.retrieveByCondition("worker", worker);
    }

    public SecretPalEvent retrieveCurrentEvent() {
        return secretPalEventRepository.retrieveEvent();
    }

    public void autoAssignRelationsFor(SecretPalEvent event, List<Worker> participants) throws Exception {
        if(participants.size() < 2) {
            throw new Exception("Can't assign with less than 2 people");
        }
        for (int i = 0; i < participants.size(); i++) {
            secretPalEventRepository.createRelationInEvent(event, participants.get(i), participants.get((i +1)% participants.size()));
        }
    }

    /*
        Dentro del template, existen: ${receiver.fullName} y ${receiver.dateOfBirth} que se  bindean
     */
    public void setEMailTemplate(Properties template) throws IOException {
        if( template.getProperty("active").equals("true") ){
            setSafePostMan( smtpPostMan );
        } else {
            setSafePostMan( dumbPostman );
        }

        File f = new File(mailTemplateProperties);
        OutputStream out = new FileOutputStream( f );
        template.store(out, "E-Mail template");
    }

    public Properties getEMailTemplate() throws IOException {
        return new PropertyParser(mailTemplateProperties);
    }
}
