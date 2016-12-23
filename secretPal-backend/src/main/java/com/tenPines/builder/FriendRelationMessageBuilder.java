package com.tenPines.builder;

import com.tenPines.application.service.MailerService;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class FriendRelationMessageBuilder extends ReminderBuilder{


    private PropertyParser templateProperties;

    public MailerService getMailerService() {
        return mailerService;
    }

    public void setMailerService(MailerService mailerService) {
        this.mailerService = mailerService;
    }

    public MailerService mailerService;
    public FriendRelationMessageBuilder() {
    }

    @Autowired
    protected String assignationSubject() throws IOException {
        return mailerService.getEMailTemplate().getSubject();
    }

    @Autowired
    protected String assignationBodyText(Worker receiver) throws IOException {
        mailerService.getEMailTemplate().setFullName(receiver.getFullName());
        mailerService.getEMailTemplate().setDateOfBirth(receiver.getDateOfBirth().toString());


        return mailerService.getEMailTemplate().getBodyText();
    }

    @Autowired
    public Message buildMessage(FriendRelation aFriendRelation) throws IOException {
        return super.buildMessage(aFriendRelation);
    }



}
