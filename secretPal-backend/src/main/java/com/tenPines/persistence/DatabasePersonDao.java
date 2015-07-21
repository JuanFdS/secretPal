package com.tenPines.persistence;

import com.tenPines.model.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
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

    private <T> T transaction(Function<Session, T> function) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        T ret = function.apply(session);

        transaction.commit();

        return ret;
    }

    @Override
    public List save(Worker... people) {
        Function<Session, List> function = session -> {
            ArrayList<java.io.Serializable> idList = new ArrayList<java.io.Serializable>();
            for (Worker person : people) {
                idList.add(session.save(person));
            }
            return idList;
        };
        return transaction(function);
    }

    @Override
    @SuppressWarnings("unchecked")
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

    @Override
    @SuppressWarnings("unchecked")
    public List<Worker> retrieveParticipants() {
        return transaction( session -> { return (List<Worker>) session.createCriteria(Worker.class)
                .add(Restrictions.eq("wantsToParticipate", true))
                .list();
        });

    }

    @Override
    public Worker retrieveAssignedFriendFor(Worker participant) {
        return null;
    }

}
