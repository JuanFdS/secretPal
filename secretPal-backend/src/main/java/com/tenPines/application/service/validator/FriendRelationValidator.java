package com.tenPines.application.service.validator;

import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.validator.rule.*;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Boolean> validateAll(List<User> validUsers){
        List<Boolean> results = new ArrayList();
        for (int i = 0; i < validUsers.size(); i++) {
            boolean result = new FriendRelationValidator(friendRelationService).validate(validUsers.get(i), validUsers.get((i + 1) % validUsers.size()));
            results.add(result);
        }
        return results;
    }

    public List<Boolean> validateHardRules(List<User> validUsers){
        List<Boolean> results = new ArrayList();
        for (int i = 0; i < validUsers.size(); i++) {
            boolean result = new FriendRelationValidator(friendRelationService).deleteSoftRules().validate(validUsers.get(i), validUsers.get((i + 1) % validUsers.size()));
            results.add(result);
        }
        return results;
    }

    public FriendRelationValidator deleteSoftRules(){
        rules = this.rules.stream().filter(r -> !r.softRule()).collect(Collectors.toList());
        return this;
    }
}
