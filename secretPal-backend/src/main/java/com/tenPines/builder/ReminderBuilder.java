package com.tenPines.builder;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;

/**
 * Created by Aye on 01/11/16.
 */
public abstract class ReminderBuilder {

    private String assignationSubject() { return "";
    }

    private String assignationBodyText(Worker birthdayWorker){return "";
    }


    public Message buildMessage(FriendRelation aFriendRelation) {
        Message message = new Message();
        message.setRecipient(aFriendRelation.getGiftGiver().geteMail());
        message.setSubject(assignationSubject());
        message.setBody(assignationBodyText(aFriendRelation.getGiftReceiver()));
        return message;
    }


}
