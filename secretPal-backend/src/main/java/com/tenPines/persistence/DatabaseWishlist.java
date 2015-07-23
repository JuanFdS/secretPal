package com.tenPines.persistence;

import com.tenPines.model.Wish;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DatabaseWishlist implements AbstractRepository<Wish> {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    @Transactional
    public void save(Wish... wishes) {
        for (Wish wish : wishes) {
            getSessionFactory().getCurrentSession().save(wish);
        }
    }

    @Override
    @Transactional
    public List<Wish> retrieveAll() {
        return getSessionFactory().getCurrentSession().createCriteria(Wish.class).list();
    }

    @Override
    @Transactional
    public Wish refresh(Wish wish) {
        getSessionFactory().getCurrentSession().refresh(wish);
        getSessionFactory().getCurrentSession().flush();
        return wish;
    }

    @Override
    @Transactional
    public void delete(Wish wish) {
        getSessionFactory().getCurrentSession().delete(wish);
    }

    @Override
    @Transactional
    public Wish findById(Long id) {
        return (Wish) getSessionFactory().getCurrentSession().get(Wish.class, id);

    }

    @Override
    @Transactional
    public void update(Wish wish) {
        getSessionFactory().getCurrentSession().update(wish);
        getSessionFactory().getCurrentSession().flush();
    }
}
