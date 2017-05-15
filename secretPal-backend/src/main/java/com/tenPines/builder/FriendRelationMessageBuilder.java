package com.tenPines.builder;

import com.tenPines.application.service.MailerService;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;

import java.io.IOException;

public class FriendRelationMessageBuilder extends ReminderBuilder{

    public MailerService mailerService;

    public MailerService getMailerService() {
        return mailerService;
    }

    public FriendRelationMessageBuilder(MailerService mailerService) {
        this.mailerService = mailerService;
    }

    protected String assignationSubject() {
        return mailerService.getEMailTemplate().getSubject();
    }

    protected String assignationBodyText(Worker receiver) {
        mailerService.getEMailTemplate().setFullName(receiver.getFullName());
        mailerService.getEMailTemplate().setDateOfBirth(receiver.getBirthday().toString());

        return mailerService.getEMailTemplate().getBodyText();
    }

    public Message buildMessage(FriendRelation aFriendRelation) {
        return super.buildMessage(aFriendRelation);
    }
}
