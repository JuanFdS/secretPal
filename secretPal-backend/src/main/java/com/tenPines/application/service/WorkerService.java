package com.tenPines.application.service;

import com.tenPines.model.User;
import com.tenPines.model.Worker;
import com.tenPines.persistence.UserRepository;
import com.tenPines.persistence.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private UserRepository userRepository;

    public Worker save(Worker newWorker) {
        return workerRepository.save(newWorker);
    }

    public List<Worker> getAllParticipants() {
        Worker example = new Worker();
        example.setWantsToParticipate(true);
        return workerRepository.findAll(Example.of(example));
    }

    public void remove(Worker aWorker) {
        //TODO: y debería sacar las relaciones asociadas; y arreglarlas de alguna manera
        workerRepository.delete(aWorker);
    }

    public void removeAll() {
        workerRepository.deleteAll();
    }

    public Worker retrieveWorkerByEmail(String email) {
        return workerRepository.findByeMail(email).stream().findFirst().orElseThrow(
                () -> new RuntimeException("The userRepository does not exist")
        );
    }

    public User retrieveUserByUserName(String userName) {
        return userRepository.findByUserName(userName).stream().findFirst().orElseThrow(
                () -> new RuntimeException("The ser does not exist")
        );
    }
    public List<Worker> retrieveParticipants() {
        return workerRepository.findBywantsToParticipate(true);
    }

    public Worker retriveWorker(Long to) {
        return workerRepository.findOne(to);
    }
}
