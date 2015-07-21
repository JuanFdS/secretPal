package com.tenPines.persistence;

import com.tenPines.model.Worker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryWorkerDao implements AbstractRepository<Worker> {

    protected static List<Worker> workers = new ArrayList<>();

    @Override
    public List<Worker> retrieveAll() {
        return workers;
    }

    @Override
    public List save(Worker... people) {
        Collections.addAll(workers, people);
        return null;
    }

    @Override
    public Worker refresh(Worker worker) {
        return worker;
    }

    @Override
    public void delete(Worker aWorker) {
        workers.remove(aWorker);
    }

    @Override
    public Worker findById(Long id) {
        return workers.get(id.intValue() - 1);
    }

    @Override
    public void update(Worker element) {

    }

    @Override
    public List<Worker> retrieveParticipants() {
        return null;
    }

    @Override
    public Worker retrieveAssignedFriendFor(Worker participant) {
        return null;
    }

}
