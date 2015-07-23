package com.tenPines.persistence;

import com.tenPines.model.Worker;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DatabaseWorkerDao implements AbstractRepository<Worker> {

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
    public void save(Worker... people) {
        for (Worker person : people) {
            getSessionFactory().getCurrentSession().save(person);
        }
    }

    @Override
    @Transactional
    public List<Worker> retrieveAll() {
        return getSessionFactory().getCurrentSession().createCriteria(Worker.class).list();
    }

    @Override
    @Transactional
    public Worker refresh(Worker worker) {
        getSessionFactory().getCurrentSession().refresh(worker);
        getSessionFactory().getCurrentSession().flush();
        return worker;
    }

    @Override
    @Transactional
    public void delete(Worker aWorker) {
        getSessionFactory().getCurrentSession().delete(aWorker);
    }

    @Override
    @Transactional
    public Worker findById(Long id) {
        return (Worker) getSessionFactory().getCurrentSession().get(Worker.class, id);

    }

    @Override
    @Transactional
    public void update(Worker element) {
        getSessionFactory().getCurrentSession().update(element);
        getSessionFactory().getCurrentSession().flush();
    }

}
