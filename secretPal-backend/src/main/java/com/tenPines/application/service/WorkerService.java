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
                () -> new RuntimeException("The user with this email does not exist")
        );
    }
}
