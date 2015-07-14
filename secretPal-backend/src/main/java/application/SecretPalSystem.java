package application;

import model.Person;
import persistence.AbstractRepository;
import persistence.DatabasePersonDao;
import persistence.InMemoryPersonDao;

import java.util.List;


public class SecretPalSystem {

    private static AbstractRepository<Person> personRepository = new DatabasePersonDao();

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
}
