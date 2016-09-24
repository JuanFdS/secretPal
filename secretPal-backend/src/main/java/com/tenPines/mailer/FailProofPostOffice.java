package com.tenPines.mailer;

import com.tenPines.model.Message;
import com.tenPines.persistence.FailedMailsRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FailProofPostOffice implements PostOffice {

    private static Logger logger = Logger.getLogger("service");

    @Autowired
    private PostMan postMan;
    @Autowired
    private FailedMailsRepository failedMails;

    @Override
    public void sendMessage(Message message) {
        try {
            postMan.sendMessage(message);
        } catch (UnableToSendMessage e) {
            UnsentMessage unsentMessage = UnsentMessage.create(message, e);
            failedMails.save(unsentMessage);
        }
    }

    @Override
    public void changePostMan(PostMan postMan){
        this.postMan = postMan;
    }

    @Override
    public List<UnsentMessage> getFailedMessages() {
        return failedMails.findAll();
    }

    @Scheduled(fixedDelay = 86400000) //86400000 = 1 dia
    public void resendFailedMessages() {
        logger.info("Resending failed mails");
        failedMails.findAll().stream().forEach(
                (unSentMessage) -> {
                    failedMails.delete(unSentMessage);
                    sendMessage(unSentMessage.toMessage());
                }
        );
    }
}
