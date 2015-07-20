package com.tenPines.persistence;

import com.tenPines.builder.PersonBuilder;
import com.tenPines.model.Worker;
import org.hibernate.cfg.Environment;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DatabaseWorkerDAOTest {

    private AbstractRepository<Worker> personDao;


    @Before
    public void setUp() {
        HibernateUtils.addConfiguration(Environment.URL, "jdbc:mysql://localhost/calendardbtest");
        HibernateUtils.addConfiguration(Environment.HBM2DDL_AUTO, "create-drop");

        this.personDao = new DatabasePersonDao(HibernateUtils.createSessionFactory());
    }

    @Test
    public void When_I_Have_Zero_Persons_Persisted_When_I_Retrieve_Then_The_List_Is_Empty() {
        List<Worker> result = this.personDao.retrieveAll();
        assertThat(result, hasSize(0));
    }

    @Test
    public void When_I_Save_A_New_Person_Then_I_Have_One_More_Person_Persisted() {
        Worker aWorker = new PersonBuilder().build();
        personDao.save(aWorker);

        List<Worker> result = this.personDao.retrieveAll();

        assertThat(result, hasSize(1));
        assertThat(result, hasItem(aWorker));
    }

    @Test
    public void When_I_Delete_A_Person_It_Should_Be_No_More() {
        Worker aWorker = new PersonBuilder().build();
        personDao.save(aWorker);

        personDao.delete(aWorker);
        List<Worker> result = this.personDao.retrieveAll();

        assertThat(result, not(hasItem(aWorker)));
    }
}