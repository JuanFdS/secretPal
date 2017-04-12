package com.tenPines.application.service.validator.rule;

import com.tenPines.application.service.FriendRelationService;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.User;

import java.time.LocalDate;

public class NotTheSameReceiverOfLastYearRule implements ValidFriendRelationRule {

    public FriendRelationService friendRelationService;

    public NotTheSameReceiverOfLastYearRule(FriendRelationService friendRelationSv){
        friendRelationService = friendRelationSv;
    }

    @Override
    public boolean evaluate(User giver, User receiver) {
        FriendRelation friendRelation = friendRelationService.getByWorkerReceiver(receiver.getWorker());
        return friendRelation.getGiftGiver().equals(giver.getWorker()) && friendRelation.getCreationDate().getYear() < LocalDate.now().plusYears(-1).getYear();
    }
}
