package com.tenPines.builder;


import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;

public class HappyBithdayMessageBuilder {


    private String assignationSubject(Worker birthdayWorker) {
        return "Feliz cumpleaños " + birthdayWorker.getFullName();
    }

    private String assignationBodyText(){
        return "Feliz cumpleaños y que seas muy feliz!";
    }

    public Message buildMesage(Worker birthdaysWorker) {
        Message message = new Message();
        message.setRecipient("ayelen.garcia@10pines.com");
        message.setSubject(assignationSubject(birthdaysWorker));
        message.setBody(assignationBodyText());
        return message;
    }
}
