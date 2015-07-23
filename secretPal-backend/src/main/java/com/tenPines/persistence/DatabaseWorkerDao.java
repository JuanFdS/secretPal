package com.tenPines.persistence;

import com.tenPines.model.Worker;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class DatabaseWorkerDao extends HibernateGenericDAO<Worker>{


    @Override
    protected Class<Worker> getDomainClass() {
        return Worker.class;
    }


}

