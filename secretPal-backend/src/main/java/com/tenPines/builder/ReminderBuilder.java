package com.tenPines.builder;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Message;
import com.tenPines.model.Worker;

import java.io.IOException;

/**
 * Created by Aye on 01/11/16.
 */
public abstract class ReminderBuilder {


    public Message buildMessage(FriendRelation aFriendRelation) throws IOException {
        return new Message(
                aFriendRelation.getGiftGiver().geteMail(),
                assignationSubject(),
                assignationBodyText(aFriendRelation.getGiftReceiver())
        );
    }


    protected abstract String assignationSubject() throws IOException;

    protected abstract String assignationBodyText(Worker birthdayWorker) throws IOException;
}
