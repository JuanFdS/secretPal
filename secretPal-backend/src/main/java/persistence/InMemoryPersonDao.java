package persistence;

import model.Person;

import java.util.ArrayList;
import java.util.List;


public class InMemoryPersonDao implements AbstractRepository<Person> {

    protected static List<Person> persons = new ArrayList<Person>();

    @Override
    public List<Person> retrieveAll() {
        return persons;
    }

    @Override
    public void save(Person... people) {
        for (Person person : people){
            persons.add(person);
        }
    }

    @Override
    public void refresh(Person person){ }

    @Override
    public Person findById(int id) {
        return persons.get(id);
    }


}
