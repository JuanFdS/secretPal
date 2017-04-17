package com.tenPines.model;

import com.tenPines.application.service.FriendRelationService;
import com.tenPines.builder.UserFactory;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.integration.SpringBaseTest;
import com.tenPines.model.process.AssignmentException;
import com.tenPines.model.process.RelationEstablisher;
import com.tenPines.persistence.UserRepository;
import com.tenPines.persistence.WorkerRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.tenPines.model.process.AssignmentException.Reason.CANT_SELF_ASSIGN;
import static com.tenPines.model.process.AssignmentException.Reason.DOES_NOT_WANT_TO_PARTICIPATE;
import static com.tenPines.model.process.AssignmentException.Reason.RECEIVER_NULL;
import static com.tenPines.model.process.AssignmentException.Reason.GIVER_NULL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class FriendRelationServiceTest extends SpringBaseTest {

    @Autowired
    private FriendRelationService friendRelationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkerRepository workerRepository;
    private List<User> users;

    @Before
    public void setUp(){
        users = new ArrayList();
        UserFactory.setRepositories(userRepository, workerRepository);
    }

    @Test
    public void WhenIShuffleThreeValidUsers(){
        User first = UserFactory.newUser();
        first.getWorker().setDateOfBirth(first.getWorker().getDateOfBirth().plusWeeks(2).plusDays(1));
        User second = UserFactory.newUser();
        second.getWorker().setDateOfBirth(second.getWorker().getDateOfBirth().plusWeeks(2).plusDays(2));
        User third = UserFactory.newUser();
        third.getWorker().setDateOfBirth(third.getWorker().getDateOfBirth().plusWeeks(2).plusDays(3));
        users.add(first);
        users.add(second);
        users.add(third);
        friendRelationService.shuffle(users);
    }
}

