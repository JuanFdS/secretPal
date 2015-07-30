package com.tenPines.mailer;

import com.tenPines.model.Message;
import com.tenPines.persistence.AbstractRepository;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;

public class FailProofPostMan implements SafePostMan {

    protected static Logger logger = Logger.getLogger("service");

    private UnsafePostMan postMan;
    private AbstractRepository<Message> failedMails;

    public SafePostMan setPostMan(UnsafePostMan postMan) {
        this.postMan = postMan;
        return this;
    }

    public void setFailedMails(AbstractRepository<Message> failedMails) {
        this.failedMails = failedMails;
    }

    @Override
    public void sendMessage(Message message) {
        try {
            postMan.sendMessage(message);
        } catch (MessagingException e) {
            message.setError(e);
            failedMails.save(message);
        }
    }

    @Scheduled(fixedDelay = 86400000) //86400000 = 1 dia
    public void resendFailedMessages() {
        logger.info("Resending failed mails");
        failedMails.retrieveAll().stream().forEach(this::sendMessage);
    }
}
