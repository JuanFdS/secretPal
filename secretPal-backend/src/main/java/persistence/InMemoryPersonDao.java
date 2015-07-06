package persistence;

import model.Person;

import java.util.ArrayList;
import java.util.List;

public class InMemoryPersonDao implements AbstractRepository<Person> {

    protected static List<Person> persons = new ArrayList<Person>();

    @Override
    public List<Person> retrieveAll() {
        return this.persons;
    }

    @Override
    public void save(Person element) {
        persons.add(element);
    }
}