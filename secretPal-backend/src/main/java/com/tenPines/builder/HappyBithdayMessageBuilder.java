package com.tenPines.builder;


import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;

import java.io.IOException;

public class HappyBithdayMessageBuilder {

    private PropertyParser templateProperties;

    public HappyBithdayMessageBuilder() {
        try {
            templateProperties = new PropertyParser("mailTemplate.properties");
        } catch (IOException e) {

        }
    }

    private String assignationSubject(Worker birthdayWorker) {
        return "Feliz cumpleaños " + birthdayWorker.getFullName();
    }

    private String assignationBodyText(){
        return "Feliz cumpleaños y que seas muy feliz!";
    }

    public Message buildMesage(Worker birthdaysWorker) {
        Message message = new Message();
        message.setRecipient(templateProperties.getProperty("receipt"));
        message.setSubject(assignationSubject(birthdaysWorker));
        message.setBody(assignationBodyText());
        return message;
    }
}
