package com.tenPines.builder;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;

import java.io.IOException;

/**
 * Created by Aye on 01/11/16.
 */
public class ReminderAproachTheBirthdayBuilder extends ReminderBuilder {

    private PropertyParser templateProperties;

    public ReminderAproachTheBirthdayBuilder() {
        try {
            templateProperties = new PropertyParser("src/main/resources/mailTemplate.properties");
        } catch (IOException e) {

        }
    }

    private String assignationSubject() {
        return "[SecretPal-Reminder] Se acerca el cumpleaños de tu pino!";
    }

    private String assignationBodyText(Worker birthdayWorker){
        return "Faltan 7 días para el cumple de " + birthdayWorker.getFullName() + "\nNo cuelgues!";
    }


}

