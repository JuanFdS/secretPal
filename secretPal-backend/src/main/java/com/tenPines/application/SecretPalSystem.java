package com.tenPines.application;

import com.tenPines.model.Person;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.persistence.AbstractRepository;
import com.tenPines.persistence.DatabasePersonDao;
import com.tenPines.persistence.DatabaseSecretPalEventDao;
import com.tenPines.persistence.HibernateUtils;

import java.util.List;


public class SecretPalSystem {

    private AbstractRepository<Person> personRepository = new DatabasePersonDao(HibernateUtils.createSessionFactory());
    private AbstractRepository<SecretPalEvent> secretPalEventRepository = new DatabaseSecretPalEventDao();

    public void savePerson(Person newPerson) {
        this.personRepository.save(newPerson);
    }

    public List<Person> retrieveAllPeople() {
        return personRepository.retrieveAll();
    }

    public SecretPalEvent retrieveASecretPalEvent(Long event_id) {
        return secretPalEventRepository.findById(event_id);
    }

    public List<SecretPalEvent> retrieveAllSecretPalEvents() {
        return secretPalEventRepository.retrieveAll();
    }

    public Person retrieveAPerson(Long id) {
        return personRepository.findById(id);
    }

    public void deletePerson(Person aPerson) {
        personRepository.delete(aPerson);
    }
}
