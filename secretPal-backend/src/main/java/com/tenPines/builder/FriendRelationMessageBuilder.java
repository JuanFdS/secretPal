package com.tenPines.builder;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;

import java.io.IOException;
import java.util.Properties;

public class FriendRelationMessageBuilder {

    private Properties templateProperties;

    public FriendRelationMessageBuilder() {
        try {
            templateProperties = new PropertyParser("src/main/resources/mailTemplate.properties");
        } catch (IOException e) {
            templateProperties.setProperty("mail.subject", "[SecretPal] Se te asigno un amigo invisible!");
            templateProperties.setProperty("mail.bodyText", "Vas a ser el amigo invisible de ${receiver.fullName}.\\nCumple el: ${receiver.dateOfBirth}.");
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
