package com.tenPines.mailer;

import com.tenPines.model.Message;
import com.tenPines.persistence.AbstractRepository;

import javax.mail.MessagingException;

public class FailProofPostMan implements PostMan {
    private PostMan postMan;
    private AbstractRepository<Message> failedMails;

    public PostMan setPostMan(PostMan postMan) {
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
            failedMails.save(message);
        }
    }
}
