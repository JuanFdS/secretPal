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

    protected String assignationSubject() throws IOException {
        return mailerService.getEMailTemplate().getSubject();
    }

    protected String assignationBodyText(Worker receiver) throws IOException {
        mailerService.getEMailTemplate().setFullName(receiver.getFullName());
        mailerService.getEMailTemplate().setDateOfBirth(receiver.getDateOfBirth().toString());

        return mailerService.getEMailTemplate().getBodyText();
    }

    public Message buildMessage(FriendRelation aFriendRelation) throws IOException {
        return super.buildMessage(aFriendRelation);
    }
}
