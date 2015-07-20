package application;

import model.Worker;
import persistence.AbstractRepository;
import persistence.InMemoryPersonDao;

import java.util.List;


public class SecretPalSystem {

    private static AbstractRepository personRepository = new InMemoryPersonDao();

    public void savePerson(Worker newWorker){
        this.personRepository.save(newWorker);
    }

    public List<Worker> retrieveAllPersons(){
        return personRepository.retrieveAll();
    }

    public static AbstractRepository getPersonRepository() {
        return personRepository;
    }

    public static void setPersonRepository(AbstractRepository personRepository) {
        SecretPalSystem.personRepository = personRepository;
    }
}
