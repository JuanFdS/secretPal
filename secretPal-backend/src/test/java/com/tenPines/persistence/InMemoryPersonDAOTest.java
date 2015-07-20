package com.tenPines.persistence;

import com.tenPines.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InMemoryPersonDAOTest {

    private AbstractRepository<Person> personDao;

    @Before
    public void setUp() {
        this.personDao = new InMemoryPersonDao();
    }

    @Test
    public void When_I_Have_Zero_Persons_Persisted_When_I_Retrieve_Then_The_List_Is_Empty() {
        List<Person> result = this.personDao.retrieveAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void When_I_Save_A_New_Person_Then_I_Have_One_More_Person_Persisted() {
        Person aPerson = new Person("Grillo Pepe", "pepegrillo@example.com", LocalDate.of(1993, Month.APRIL, 12));
        this.personDao.save(aPerson);
        List<Person> result = this.personDao.retrieveAll();
        assertEquals("The list should Have One More Person", result.size(), 1);

    }

}
