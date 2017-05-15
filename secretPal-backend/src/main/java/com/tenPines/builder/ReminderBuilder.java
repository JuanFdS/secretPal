package com.tenPines.builder;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;

import java.io.IOException;

/**
 * Created by Aye on 01/11/16.
 */
public abstract class ReminderBuilder {


    public Message buildMessage(FriendRelation aFriendRelation) {
        return new Message(
                aFriendRelation.getGiftGiver().getMail(),
                assignationSubject(),
                assignationBodyText(aFriendRelation.getGiftReceiver())
        );
    }


    protected abstract String assignationSubject();

    protected abstract String assignationBodyText(Worker birthdayWorker);
}
