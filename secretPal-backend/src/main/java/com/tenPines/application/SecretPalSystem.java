package com.tenPines.application;

import com.tenPines.model.Worker;
import com.tenPines.persistence.*;

import java.util.List;


public class SecretPalSystem {

    private AbstractRepository<Worker> personRepository = new DatabasePersonDao(HibernateUtils.createSessionFactory());

    public void savePerson(Worker newWorker) {
        this.personRepository.save(newWorker);
    }

    public List<Worker> retrieveAllPeople() {
        return personRepository.retrieveAll();
    }

    public Worker retrieveAPerson(Long id) {
        return personRepository.findById(id);
    }

    public void deletePerson(Worker aWorker) {
        personRepository.delete(aWorker);
    }

    public void changeIntention(Worker aWorker) {
        Worker worker = getWorker(aWorker);
        worker.changeParticipationIntention(aWorker.getWantsToParticipate());
    }

    public Worker getWorker(Worker aWorker) {
        return personRepository.refresh(aWorker);
    }
}
