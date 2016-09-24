package com.tenPines.application.service;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;
import com.tenPines.persistence.FriendRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRelationService {
    @Autowired
    private FriendRelationRepository friendRelationRepository;

    public FriendRelation create(Worker friendWorker, Worker birthdayWorker) {
        return friendRelationRepository.save(new FriendRelation(friendWorker, birthdayWorker));
    }

    public List<FriendRelation> getAllRelations() {
        return friendRelationRepository.findAll();
    }

}
