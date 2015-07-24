package com.tenPines.persistence;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class HibernateGenericDAO<T> implements AbstractRepository<T>, Serializable {

    private static final long serialVersionUID = 5058950102420892922L;
    protected Class<T> persistentClass = this.getDomainClass();

    @Autowired
    private SessionFactory sessionFactory;


    protected abstract Class<T> getDomainClass();

    public SessionFactory getSessionFactory() { return sessionFactory; }

    public void setSessionFactory(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    @Override
    public void delete(final T entity) {
        getSessionFactory().getCurrentSession().delete(entity);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<T> retrieveAll() {
        return getSessionFactory().getCurrentSession().createCriteria(persistentClass).list();

    }

    @Override
    @SuppressWarnings("unchecked")
    public T findById(Long id) {
        return (T) getSessionFactory().getCurrentSession().get(this.persistentClass, id);
    }

    @Override
    public T save(T entity) {
        Session session = getSessionFactory().getCurrentSession();
        Long persistedID = (Long) getSessionFactory().getCurrentSession().save(entity);
        session.flush();
        return findById(persistedID);
    }

    @Override
    public void update(final T entity) {
        getSessionFactory().getCurrentSession().update(entity);
        getSessionFactory().getCurrentSession().flush();
    }

    @Override
    public T refresh(T entity){
        getSessionFactory().getCurrentSession().refresh(entity);
        getSessionFactory().getCurrentSession().flush();
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> retrieveByCondition(String property, Object value) {
        return getSessionFactory().getCurrentSession()
                .createCriteria(persistentClass)
                .add(Restrictions.eq(property, value))
                .list();
    }

}
