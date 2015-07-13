package persistence;

import builder.PersonBuilder;
import model.Person;
import model.SecretPalEvent;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabasePersonDAOTest {

    private AbstractRepository<Person> personDao;
    private AbstractRepository<SecretPalEvent> secretPalEventDao;

    @Before
    public void setUp(){
        this.personDao = new DatabasePersonDao();
        this.secretPalEventDao = new DatabaseSecretPalEventDao();
    }

    @Test
    public void When_I_Have_Zero_Persons_Persisted_When_I_Retrieve_Then_The_List_Is_Empty(){
        List<Person> result = this.personDao.retrieveAll();
        assertTrue(result.isEmpty());
    }

    @Test
    public void When_I_Save_A_New_Person_Then_I_Have_One_More_Person_Persisted() {
        Person aPerson = new PersonBuilder().build();
        personDao.save(aPerson);

        List<Person> result = this.personDao.retrieveAll();

        //TODO Los tests usan la misma DB y no la limpian assertEquals("The list should Have One More Person", result.size(), 1);
        assertTrue("The list should contain the new person elements", result.contains(aPerson));
    }

    @Test
    public void When_I_Add_a_Participant_It_should_stay_persisted_and_as_a_participant(){
        Person aParticipant =  new PersonBuilder().build();
        Person aPerson =  new PersonBuilder().build();
        SecretPalEvent secretPalEvent = new SecretPalEvent();

        //aParticipant.getSecretPalEvents().add(secretPalEvent);
        secretPalEvent.getParticipants().add(aParticipant);
        personDao.save(aPerson);
        secretPalEventDao.save(secretPalEvent);

        secretPalEventDao.refresh( secretPalEvent ); //recarga de la DB

        assertTrue("The participant should be saved as such", secretPalEvent.getParticipants().contains(aParticipant));
        assertFalse("The non-participant should not be saved as participant", secretPalEvent.getParticipants().contains(aPerson));
    }

}