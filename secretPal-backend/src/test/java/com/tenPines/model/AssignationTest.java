package com.tenPines.model;

import com.tenPines.builder.WorkerBuilder;
import org.junit.Test;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class AssignationTest {


    List<Worker> workerList = new ArrayList<>();
    Map<Worker, Worker> assignment = null;
    WorkerBuilder workerBuilder = new WorkerBuilder();

    @Test
    public void When_there_is_no_person_the_assignation_should_give_an_error(){
        try {
            assignment = assign(workerList);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't assign with less than 2 people");
        }
        assertEmptyAssignment();
    }

    private void assertEmptyAssignment() {
        assertEquals(assignment, null);
    }

    @Test
    public void When_there_is_only_one_person_the_assignation_should_give_an_error() throws Exception {
        workerList.add(workerBuilder.build());
        try {
            assignment = assign(workerList);
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Can't assign with less than 2 people");
        }
        assertEmptyAssignment();
    }
    @Test
    public void When_there_are_two_people_the_assignation_should_give_each_other() throws Exception {
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
    @Test
    public void When_there_are_three_people_the_assignation_should_not_give_each_other() throws Exception {
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

    private void assertNoDualGift() {
        for( Worker p : workerList){
            // Que el que tiene asignado P no le regale a P
            assertNotGift(assignment.get(p), p);
        }
    }

    private void assertNoSelfGift() {
        for( Worker p : workerList){
            assertNotGift(p, p);
        }
    }

    private void assertGift(Worker gifter, Worker gifted) {
        assertEquals(assignment.get(gifter), gifted);
    }

    private void assertNotGift(Worker gifter, Worker gifted) {
        assertNotEquals(assignment.get(gifter), gifted);
    }

    private void assertNotEmptyAssignment() {
        assertTrue(assignment.size() > 0);
    }

    private Map<Worker, Worker> assign(List<Worker> workerList) throws Exception {
        LinkedHashMap<Worker, Worker> assignment = new LinkedHashMap<>();

        if(workerList.size() < 2) {
            throw new Exception("Can't assign with less than 2 people");
        }

        for (int i = 0; i < workerList.size(); i++) {
            assignment.put(workerList.get(i), workerList.get((i +1)% workerList.size()));
        }

        return assignment;
    }

}