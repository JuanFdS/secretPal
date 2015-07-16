package application;

import model.Person;
import model.SecretPalEvent;
import persistence.AbstractRepository;
import persistence.DatabasePersonDao;
import persistence.DatabaseSecretPalEventDao;

import java.util.List;


public class SecretPalSystem {

    private static AbstractRepository<Person> personRepository = new DatabasePersonDao();
    private static AbstractRepository<SecretPalEvent> secretPalEventRepository = new DatabaseSecretPalEventDao();

    public void savePerson(Person newPerson){
        this.personRepository.save(newPerson);
    }

    public List<Person> retrieveAllPersons(){
        return personRepository.retrieveAll();
    }

    public static AbstractRepository getPersonRepository() {
        return personRepository;
    }

    public static void setPersonRepository(AbstractRepository personRepository) {
        SecretPalSystem.personRepository = personRepository;
    }

    public SecretPalEvent retrieveASecretPalEvent(int event_id) {
        return secretPalEventRepository.findById(event_id) ;
    }

    public List<SecretPalEvent> retrieveAllSecretPalEvents() {
        return secretPalEventRepository.retrieveAll();
    }

    public Person retrieveAPerson(int id) {
        return personRepository.findById( id );
    }
}
