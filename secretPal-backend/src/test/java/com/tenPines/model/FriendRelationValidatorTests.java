package com.tenPines.model;

import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.validator.FriendRelationValidator;
import com.tenPines.builder.UserFactory;
import com.tenPines.model.stubs.RepoRelationsStub;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FriendRelationValidatorTests {

    public FriendRelationValidator friendRelationValidator;
    public FriendRelationService friendRelationService;

    @Before
    public void setUp(){
        friendRelationService = new FriendRelationService();
        friendRelationService.friendRelationRepository = new RepoRelationsStub();
        friendRelationValidator = new FriendRelationValidator(friendRelationService);
    }

    @Test
    public void whenAUserIsHimselfItShouldNotBeValid(){
       User user = UserFactory.newUser();
       assertThat(friendRelationValidator.validate(user,user)).isEqualTo(false);
    }

    @Test
    public void whenTheyWereBornTheSameDayItShouldNotBeValid(){
        User giver = UserFactory.newUser();
        User receiver = UserFactory.newUser();

        assertThat(friendRelationValidator.validate(giver,receiver)).isEqualTo(false);
    }

    @Test
    public void whenTheReceiverWillGiftMeItShouldNotBeValid(){
        User giver = UserFactory.newUser();
        giver.getWorker().setDateOfBirth(giver.getWorker().getDateOfBirth().plusWeeks(2).plusDays(1));
        User receiver = UserFactory.newUser();
        friendRelationService.create(giver.getWorker(), receiver.getWorker());
        assertThat(friendRelationValidator.validate(receiver,giver)).isEqualTo(false);
    }

    @Test
    public void whenTheReceiversBirthdayIsTooClosetItShouldNotBeValid(){
        User giver = UserFactory.newUser();
        User receiver = UserFactory.newUser();
        receiver.getWorker().setDateOfBirth(receiver.getWorker().getDateOfBirth().withYear(1990));
        assertThat(friendRelationValidator.validate(giver,receiver)).isEqualTo(false);
    }

    @Test
    public void whenTheReceiversBirthdayIsNotTooClosetItShouldBeValid(){
        User giver = UserFactory.newUser();
        User receiver = UserFactory.newUser();
        receiver.getWorker().setDateOfBirth(receiver.getWorker().getDateOfBirth().plusWeeks(2).plusDays(1));
        assertThat(friendRelationValidator.validate(giver,receiver)).isEqualTo(true);
    }

    @Test
    public void whenTheReceiverIsTheSameOfTheLastYearItShouldNotBeValid(){
        User giver = UserFactory.newUser();
        User receiver = UserFactory.newUser();
        receiver.getWorker().setDateOfBirth(receiver.getWorker().getDateOfBirth().plusWeeks(2).plusDays(1));
        friendRelationService.create(giver.getWorker(), receiver.getWorker());
        FriendRelation friendRelation = friendRelationService.getByWorkerReceiver(receiver.getWorker());
        friendRelation.setCreationDate(friendRelation.getCreationDate().plusYears(-1));
        assertThat(friendRelationValidator.validate(giver,receiver)).isEqualTo(false);
    }

    @Test
    public void whenTheReceiverIsPreviousTheLastYearItShouldBeValid(){
        User giver = UserFactory.newUser();
        User receiver = UserFactory.newUser();
        receiver.getWorker().setDateOfBirth(receiver.getWorker().getDateOfBirth().plusWeeks(2).plusDays(1));
        friendRelationService.create(giver.getWorker(), receiver.getWorker());
        FriendRelation friendRelation = friendRelationService.getByWorkerReceiver(receiver.getWorker());
        friendRelation.setCreationDate(friendRelation.getCreationDate().plusYears(-2));
        assertThat(friendRelationValidator.validate(giver,receiver)).isEqualTo(true);
    }
}
