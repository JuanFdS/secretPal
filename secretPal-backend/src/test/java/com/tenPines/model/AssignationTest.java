package com.tenPines.model;

import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.process.AssignmentException;
import com.tenPines.model.process.AssignmentFunction;
import org.junit.Test;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;


public class AssignationTest {


    private List<Worker> workerList = new ArrayList<>();
    private WorkerBuilder workerBuilder = new WorkerBuilder();
    private List<FriendRelation> assignment = new ArrayList<>();

    @Test  //Cuando no hay persona la asignación debe dar un error
    public void When_there_is_no_person_the_assignation_should_give_an_error(){
        try {
            assign(workerList);
        } catch (AssignmentException e) {
            assertEquals(e.getReason(), AssignmentException.Reason.NOT_ENOUGH_QUORUM);
        }
        assertEmptyAssignment();
    }

    @Test //Cuando sólo hay una persona la asignación debe dar un error
    public void When_there_is_only_one_person_the_assignation_should_give_an_error() {
        workerList.add(workerBuilder.build());
        try {
            assignment = assign(workerList);
        } catch (AssignmentException e) {
            assertEquals(e.getReason(), AssignmentException.Reason.NOT_ENOUGH_QUORUM);
        }
        assertEmptyAssignment();
    }

    @Test  //Cuando hay dos personas la asignación debe darse el uno al otro
    public void When_there_are_two_people_the_assignation_should_give_each_other() {
        Worker ajani = workerBuilder.buildFromDate(1, Month.JANUARY);
        Worker chandra = workerBuilder.buildFromDate(5, Month.JANUARY);

        workerList.add(ajani);
        workerList.add(chandra);

        assignment = assign(workerList);
        assertNotEmptyAssignment();

        assertEquals(assignment.size(), 2);
        assertGift(ajani, chandra);
        assertGift(chandra, ajani);
    }
    @Test //Cuando hay tres personas la asignación no debe darse el uno al otro
    public void When_there_are_three_people_the_assignation_should_not_give_each_other() {
        Worker ajani = workerBuilder.buildFromDate(1, Month.JANUARY);
        Worker chandra = workerBuilder.buildFromDate(5, Month.JANUARY);
        Worker dack = workerBuilder.buildFromDate(10, Month.JANUARY);

        workerList.add(ajani);
        workerList.add(chandra);
        workerList.add(dack);

        assignment = assign(workerList);
        assertNotEmptyAssignment();

        assertNoSelfGift();
        assertNoDualGift();
    }







    private void assertEmptyAssignment() {
        assertThat(assignment, empty());
    }

    private void assertNoDualGift() {
        workerList.stream().forEach(p -> {
            // Que el que tiene asignado P no le regale a P
            assertNotGift(getGiftReceiverFor(p), p);
        });
    }

    private void assertNoSelfGift() {
        for( Worker p : workerList){
            assertNotGift(p, p);
        }
    }

    private void assertGift(Worker gifter, Worker gifted) {
        assertEquals( getGiftReceiverFor(gifter), gifted);
    }

    private Worker getGiftReceiverFor(Worker gifter) {
        return assignment.stream()
                .filter(friendRelation -> friendRelation.getGiftGiver().equals(gifter))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Gifter not found")
                ).getGiftReceiver();
    }

    private void assertNotGift(Worker gifter, Worker gifted) {
        assertNotEquals( getGiftReceiverFor(gifter), gifted);
    }

    private void assertNotEmptyAssignment() {
        assertTrue(assignment.size() > 0);
    }

    private List<FriendRelation> assign(List<Worker> workerList) throws AssignmentException{
        return new AssignmentFunction(workerList).execute();
    }

}