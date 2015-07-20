package application;

import model.Worker;
import persistence.AbstractRepository;
import persistence.InMemoryPersonDao;

import java.util.List;


public class SecretPalSystem {

    private static AbstractRepository<Worker> personRepository = new InMemoryPersonDao();

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

    public void changeIntention(Worker aWorker) {
        Worker worker = getWorker(aWorker);
        worker.changeParticipationIntention(aWorker.wantsToParticipate());
    }

    public Worker getWorker(Worker aWorker) {
        return personRepository.find(aWorker);
    }


}
