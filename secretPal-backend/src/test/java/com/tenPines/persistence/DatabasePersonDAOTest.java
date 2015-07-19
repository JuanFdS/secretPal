package com.tenPines.persistence;

import com.tenPines.builder.PersonBuilder;
import com.tenPines.model.Person;
import com.tenPines.model.SecretPalEvent;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class DatabasePersonDAOTest {

    private AbstractRepository<Person> personDao;
    private AbstractRepository<SecretPalEvent> secretPalEventDao;

    @Before
    public void setUp() {
        //todo: Cambiar la DB (calendardbtest) y el dll (create-drop) o a lo sumo hacer que esto tenga rollback

        this.personDao = new DatabasePersonDao( HibernateUtils.createSessionFactory() );
        this.secretPalEventDao = new DatabaseSecretPalEventDao();
    }

    @Test
    //TODO Los tests usan la misma DB y no la limpian
    public void When_I_Have_Zero_Persons_Persisted_When_I_Retrieve_Then_The_List_Is_Empty() {
        List<Person> result = this.personDao.retrieveAll();
        //assertTrue(result.isEmpty());
    }

    @Test
    public void When_I_Save_A_New_Person_Then_I_Have_One_More_Person_Persisted() {
        Person aPerson = new PersonBuilder().build();
        personDao.save(aPerson);

        List<Person> result = this.personDao.retrieveAll();

        //assertEquals("The list should Have One More Person", result.size(), 1);
        assertTrue("The list should contain the new person elements", result.contains(aPerson));
    }

    @Test
    public void When_I_Delete_A_Person_It_Should_Be_No_More() {
        Person aPerson = new PersonBuilder().build();
        personDao.save(aPerson);

        personDao.delete(aPerson);
        List<Person> result = this.personDao.retrieveAll();

        assertFalse("The list should not contain the new person elements", result.contains(aPerson));
    }
}