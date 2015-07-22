package com.tenPines.persistence;

import com.tenPines.model.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Repository
public class DatabasePersonDao implements AbstractRepository<Worker> {
    private SessionFactory sessionFactory;

    public DatabasePersonDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DatabasePersonDao() {
    }

    private <T> T transaction(Function<Session, T> function) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        T ret = function.apply(session);

        transaction.commit();

        return ret;
    }

    @Override
    public void save(Worker... people) {
        Function<Session, List> function = session -> {
            ArrayList idList = new ArrayList();
            for (Worker person : people) {
                idList.add(session.save(person));
            }
            return idList;
        };
        transaction(function);
    }

    @Override
    public List<Worker> retrieveAll() {
        return transaction(session -> {
            return session.createCriteria(Worker.class).list();
        });
    }

    @Override
    public Worker refresh(Worker worker) {
        return transaction(session -> {
            session.refresh(worker);
            return worker;
        });
    }

    @Override
    public void delete(Worker aWorker) {
        transaction(session -> {
            session.delete(aWorker);
            return aWorker;
        });
    }

    @Override
    public Worker findById(Long id) {
        return transaction((Session session) -> (Worker) session.get(Worker.class, id));

    }

    @Override
    public void update(Worker element) {
        transaction(session -> { session.update(element); return element; } );
    }

}
