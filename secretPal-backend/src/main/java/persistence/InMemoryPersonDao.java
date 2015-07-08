package persistence;

import model.Person;

import java.util.ArrayList;
import java.util.List;


public class InMemoryPersonDao implements AbstractRepository<Person> {

    protected static List<Person> persons = new ArrayList<Person>();

    public static List<Person> getPersons() {
        return persons;
    }

    public static void setPersons(List<Person> persons) {
        InMemoryPersonDao.persons = persons;
    }

    @Override
    public List<Person> retrieveAll() {
        return persons;
    }

    @Override
    public void save(Person element) {
        persons.add(element);
    }
}
