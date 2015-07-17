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
    public void delete(Person aPerson) {
        persons.remove(aPerson);
    }

    @Override
    public Person findById(Long id) {
        return persons.get(id.intValue()-1);
    }


}
