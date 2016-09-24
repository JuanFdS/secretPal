package com.tenPines.builder;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;

import java.io.IOException;

public class FriendRelationMessageBuilder {

    private PropertyParser templateProperties;

    public FriendRelationMessageBuilder() {
        try {
            templateProperties = new PropertyParser("src/main/resources/mailTemplate.properties");
        } catch (IOException e) {
            templateProperties.setProperty("subject", "[SecretPal] Se te asigno un amigo invisible!");
            templateProperties.setProperty("bodyText", "Vas a ser el amigo invisible de ${fullName}.\\nCumple el: ${dateOfBirth}.");
        }
    }

    private String assignationSubject() {
        return templateProperties.getProperty("subject");
    }

    private String assignationBodyText(Worker receiver) {
        templateProperties.setProperty("fullName", receiver.getFullName());
        templateProperties.setProperty("dateOfBirth", receiver.getDateOfBirth().toString());
        return templateProperties.getProperty("bodyText");
    }


    public Message buildMessage(FriendRelation friendRelation) {
        Message message = new Message();
        message.setRecipient(friendRelation.getGiftGiver().geteMail());
        message.setSubject(assignationSubject());
        message.setBody(assignationBodyText(friendRelation.getGiftReceiver()));
        return message;
    }
}
