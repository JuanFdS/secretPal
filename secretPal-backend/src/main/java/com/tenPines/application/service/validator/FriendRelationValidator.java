package com.tenPines.application.service.validator;

import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.validator.rule.*;
import com.tenPines.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class FriendRelationValidator {

    @Autowired
    public FriendRelationService friendRelationService;
    public List<ValidFriendRelationRule> rules;

    public FriendRelationValidator(FriendRelationService friendRelationSv){
        friendRelationService = friendRelationSv;
        rules = new ArrayList<>();
        rules.add(new NotTheSamePersonRule());
        rules.add(new NotCircularRelationRule(friendRelationService));
        rules.add(new NotTheSameBirthdayRule());
        rules.add(new BirthdayIsNotTooCloseRule());
        rules.add(new NotTheSameReceiverOfLastYearRule(friendRelationService));
    }

    public boolean validate(User giver, User receiver) {
        return rules.stream().allMatch(r -> r.evaluate(giver,receiver));
    }
}
