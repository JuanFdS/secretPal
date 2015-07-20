package persistence;

import model.Worker;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class InMemoryWorkerDAOTest {

    private InMemoryPersonDao personDao;

    @Before
    public void setUp(){
        this.personDao = new InMemoryPersonDao();
        this.personDao.setWorkers(new ArrayList<Worker>());
    }

    @Test
    public void When_I_Have_Zero_Persons_Persisted_When_I_Retrieve_Then_The_List_Is_Empty(){
        List<Worker> result = this.personDao.retrieveAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void When_I_Save_A_New_Person_Then_I_Have_One_More_Person_Persisted(){
        Worker aWorker = new Worker("Grillo Pepe","pepegrillo@example.com", LocalDate.of(1993, Month.APRIL,12));
        this.personDao.save(aWorker);
        List<Worker> result = this.personDao.retrieveAll();
        assertFalse(result.isEmpty());

    }

}
