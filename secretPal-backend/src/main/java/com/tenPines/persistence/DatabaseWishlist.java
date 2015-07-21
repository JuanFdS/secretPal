package com.tenPines.persistence;

import com.tenPines.model.Wish;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DatabaseWishlist implements AbstractRepository<Wish> {
    private SessionFactory sessionFactory;

    public DatabaseWishlist(SessionFactory sessionFactory) {
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
    public List save(Wish... wishes) {
        Function<Session, List> function = session -> {
            ArrayList idList = new ArrayList();
            for (Wish wish : wishes) {
                idList.add(session.save(wish));
            }
            return idList;
        };
        return transaction(function);
    }

    @Override
    public List<Wish> retrieveAll() {
        return transaction(session -> {
            return session.createCriteria(Wish.class).list();
        });
    }

    @Override
    public Wish refresh(Wish wish) {
        return transaction(session -> {
            session.refresh(wish);
            return wish;
        });
    }

    @Override
    public void delete(Wish wish) {
        transaction(session -> {
            session.delete(wish);
            return wish;
        });
    }

    @Override
    public Wish findById(Long id) {
        return transaction((Session session) -> (Wish) session.get(Wish.class, id));

    }

    @Override
    public void update(Wish wish) {
        transaction(session -> {
            session.update(wish);
            return wish;
        });
    }
}
