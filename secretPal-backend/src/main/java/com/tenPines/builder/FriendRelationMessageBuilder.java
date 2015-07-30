package com.tenPines.builder;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;

import java.io.IOException;
import java.util.Properties;

public class FriendRelationMessageBuilder {

    private Properties templateProperties;

    public FriendRelationMessageBuilder() {
        try {
            templateProperties = PropertyBuilder.buildPropertyFrom("src/main/resources/mailTemplate.properties");
        } catch (IOException e) {
            templateProperties.setProperty("mail.subject", "[SecretPal] A secret pal was assigned to you!");
            templateProperties.setProperty("mail.bodyText", "You're the secret pal of ${receiver.fullName}. His/Her birthday is on: ${receiver.dateOfBirth}");
        }
    }

    private String assignationSubject() {
        return templateProperties.getProperty("mail.subject");
    }

    private String assignationBodyText(Worker receiver) {
        templateProperties.setProperty("receiver.fullName", receiver.getFullName());
        templateProperties.setProperty("receiver.dateOfBirth", receiver.getDateOfBirth().toString());
        return templateProperties.getProperty("mail.bodyText");
    }


    public Message buildMessage(FriendRelation friendRelation) {
        Message message = new Message();
        message.setRecipient(friendRelation.getGiftGiver().geteMail());
        message.setSubject(assignationSubject());
        message.setBody(assignationBodyText(friendRelation.getGiftReceiver()));
        return message;
    }
}
