package com.tenPines.persistence;

import com.tenPines.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Repository
public class DatabasePersonDao implements AbstractRepository<Person> {
    private SessionFactory sessionFactory;

    public DatabasePersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private <T> T transaction(Function<Session, T> function) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        T ret = function.apply(session);

        transaction.commit();

        return ret;
    }

    @Override
    public List save(Person... people) {
        Function<Session, List> function = session -> {
            ArrayList idList = new ArrayList();
            for (Person person : people) {
                idList.add(session.save(person));
            }
            return idList;
        };
        return transaction(function);
    }

    @Override
    public List<Person> retrieveAll() {
        return transaction(session -> {
            return session.createCriteria(Person.class).list();
        });
    }

    @Override
    public Person refresh(Person person) {
        return transaction(session -> {
            session.refresh(person);
            return person;
        });
    }

    @Override
    public void delete(Person aPerson) {
        transaction(session -> {
            session.delete(aPerson);
            return aPerson;
        });
    }

    @Override
    public Person findById(Long id) {
        return transaction((Session session) -> (Person) session.get(Person.class, id));

    }

}
