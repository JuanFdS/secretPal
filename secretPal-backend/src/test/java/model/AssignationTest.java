package model;

import static org.junit.Assert.*;

import builder.PersonBuilder;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;


public class AssignationTest {


    List<Person> personList = new ArrayList<>();
    Map<Person, Person> assignment = null;
    PersonBuilder personBuilder = new PersonBuilder();

    @Test
    public void When_there_is_no_person_the_assignation_should_give_an_error(){
        try {
            assignment = assign(personList);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't assign with less than 2 people");
        }
        assertEmptyAssignment();
    }

    private void assertEmptyAssignment() {
        assertEquals(assignment, null);
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
    public void When_there_are_two_people_the_assignation_should_give_each_other() throws Exception {
        Person ajani = personBuilder.buildFromDate(1, Month.JANUARY);
        Person chandra = personBuilder.buildFromDate(5, Month.JANUARY);

        personList.add(ajani);
        personList.add(chandra);

        assignment = assign(personList);
        assertNotEmptyAssignment();

        assertEquals(assignment.size(), 2);
        assertGift(ajani, chandra);
        assertGift(chandra, ajani);
    }
    @Test
    public void When_there_are_three_people_the_assignation_should_not_give_each_other() throws Exception {
        Person ajani = personBuilder.buildFromDate(1, Month.JANUARY);
        Person chandra = personBuilder.buildFromDate(5, Month.JANUARY);
        Person dack = personBuilder.buildFromDate(10, Month.JANUARY);

        personList.add(ajani);
        personList.add(chandra);
        personList.add(dack);

        assignment = assign(personList);
        assertNotEmptyAssignment();

        assertNoSelfGift();
        assertNoDualGift();
    }

    private void assertNoDualGift() {
        for( Person p : personList){
            // Que el que tiene asignado P no le regale a P
            assertNotGift(assignment.get(p), p);
        }
    }

    private void assertNoSelfGift() {
        for( Person p : personList){
            assertNotGift(p, p);
        }
    }

    private void assertGift(Person gifter, Person gifted) {
        assertEquals(assignment.get(gifter), gifted);
    }

    private void assertNotGift(Person gifter, Person gifted) {
        assertNotEquals(assignment.get(gifter), gifted);
    }

    private void assertNotEmptyAssignment() {
        assertTrue(assignment.size() > 0);
    }

    private Map<Person, Person> assign(List<Person> personList) throws Exception {
        LinkedHashMap<Person, Person> assignment = new LinkedHashMap<>();

        if(personList.size() < 2) {
            throw new Exception("Can't assign with less than 2 people");
        }

        for (int i = 0; i < personList.size(); i++) {
            assignment.put(personList.get(i), personList.get((i +1)% personList.size()));
        }

        return assignment;
    }

}