package com.tenPines.persistence;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DatabaseRepository<T> implements AbstractRepository<T> {

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
    public void save(T... elements) {
        for (T element : elements) {
            getSessionFactory().getCurrentSession().save(element);
        }
    }

    @Override
    @Transactional
    public List<T> retrieveAll() {
        return getSessionFactory().getCurrentSession().createCriteria(
                getClass().getGenericSuperclass().getClass()
        ).list();

    }

    @Override
    @Transactional
    public T refresh(T element) {
        sessionFactory.getCurrentSession().refresh(element);
        return element;
    }

    @Override
    @Transactional
    public void delete(T element) {
        getSessionFactory().getCurrentSession().delete(element);
    }

    @Override
    @Transactional
    public T findById(Long id) {
        return (T) getSessionFactory().getCurrentSession().get(
                getClass().getGenericSuperclass().getClass()
                , id);

    }

    @Override
    @Transactional
    public void update(T element) {
        getSessionFactory().getCurrentSession().update(element);
        getSessionFactory().getCurrentSession().flush();
    }

    @Override
    public List<T> retrieveByCondition(String property, Object value) {
        return null;
    }
}
