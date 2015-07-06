package persistence;

import model.Person;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class InMemoryPersonDAOTest {

    private InMemoryPersonDao personDao;

    @Before
    public void setUp(){
        this.personDao = new InMemoryPersonDao();
    }

    @Test
    public void When_I_Have_One_Person_Persisted_When_I_Retrieve_Then_The_List_Is_Not_Empty(){
        List<Person> result = this.personDao.retrieveAll();
        assertFalse(result.isEmpty());
    }
    

}
