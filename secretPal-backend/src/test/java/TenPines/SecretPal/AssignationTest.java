package TenPines.SecretPal;

import static org.junit.Assert.*;

import factories.PersonFactory;
import model.Person;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;


public class AssignationTest {


    List<Person> personList = new ArrayList<>();
    Map<Person, Person> assignment = null;

    @Test
    public void When_there_no_person_the_assignation_should_give_an_error(){
        try {
            assignment = assign(personList);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't assign with less than 2 people");
        }
        assertEmptyAssignment();
    }

    private void assertEmptyAssignment() {
        assertEquals( assignment, null );
    }

    @Test
	public void When_there_is_only_one_person_the_assignation_should_give_an_error(){
        personList.add( new Person("Pepe", "Casas", LocalDate.now()) );
        try {
            assignment = assign(personList);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't assign with less than 2 people");
        }
        assertEmptyAssignment();
    }
	@Test
	public void When_there_are_two_persons_the_assignation_should_give_each_other() throws Exception {
        Person joe = PersonFactory.fromDate(1, Month.JANUARY);
        Person bob = PersonFactory.fromDate(5, Month.JANUARY);

        personList.add(joe);
        personList.add(bob);

        assignment = assign(personList);
        assertNotEmptyAssignment();
        assertEquals(assignment.size(), 2);

        gifts(joe, bob);
    }

    private void gifts(Person joe, Person bob) {
        assertEquals(assignment.get(joe), bob);
    }

    private void assertNotEmptyAssignment() {
        assertTrue(assignment.size() > 0);
    }

    private Map<Person, Person> assign(List<Person> personList) throws Exception {
        LinkedHashMap<Person, Person> assignment = new LinkedHashMap<>();

        if(personList.size() < 2) {
            throw new Exception("Can't assign with less than 2 people");
        }

        assignment.put(personList.get(0), personList.get(1));
        assignment.put(personList.get(1), personList.get(0));

        return assignment;
    }

}
