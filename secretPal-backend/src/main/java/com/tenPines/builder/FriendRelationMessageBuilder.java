package com.tenPines.builder;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Properties;

public class FriendRelationMessageBuilder {

    private Properties templateProperties;

    public FriendRelationMessageBuilder() throws IOException {
        templateProperties = PropertyBuilder.buildPropertyFrom("src/main/resources/mailTemplate.properties");
    }

    private String assignationSubject() {
        return templateProperties.getProperty("mail.subject");
    }

    private String assignationBodyText(Worker receiver) {
        templateProperties.setProperty("receiver.fullName", receiver.getFullName());
        templateProperties.setProperty("receiver.dateOfBirth", receiver.getDateOfBirth().toString());
        return templateProperties.getProperty("mail.bodyText");
    }


    public Message buildMessage(FriendRelation friendRelation) throws MessagingException {
        Message message = new Message();
        message.setRecipient(friendRelation.getGiftGiver().geteMail());
        message.setSubject(assignationSubject());
        message.setBody(assignationBodyText(friendRelation.getGiftReceiver()));
        return message;
    }
}
