package com.tenPines.model;

import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.integration.SpringBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;


public class AssignationTest extends SpringBaseTest {

    @Autowired
    WorkerService workerService;
    @Autowired
    FriendRelationService friendRelationService;
    @Autowired
    RelationEstablisher relationEstablisher;

    @Test
    public void The_first_relation_works() {
        Worker lucas = saveWorker("Lucas David Traverso", 1994, 9, 10);
        Worker virginia = saveWorker("Virginia Carla Traverso", 1996, 9, 20);
        Worker margarita = saveWorker("Margarita Elizabeth Silva", 1965, 9, 15);

        FriendRelation relation = relationEstablisher.buildRelationFor(lucas, 2015);
        assertThat(relation, hasProperty("giftReceiver", equalTo(lucas)));
        assertThat(relation, hasProperty("wasFulfilled", equalTo(false)));
    }
//
//    @Test
//    public void crazy_testing() {
//        Worker lucas = saveWorker("Lucas", 1994, 9, 10);
//        Worker horacio = saveWorker("horacio", 1962, 1, 8);
//        Worker virginia = saveWorker("Virginia", 1996, 9, 20);
//        Worker margarita = saveWorker("Margarita", 1965, 9, 15);
//        Worker migue = saveWorker("Miguel", 1991, 9, 6);
//        Worker juan = saveWorker("Juan", 1994, 7, 9);
//        Worker gus = saveWorker("Gustavo", 1994, 2, 25);
//        Worker aye = saveWorker("Aye", 1992, 3, 26);
//        Worker dani = saveWorker("Daniel", 1993, 2, 12);
//
//        System.out.println("cosas");
//    }

    private Worker saveWorker(String fullName, int anio, int mes, int dia) {
        return workerService.save(
                new WorkerBuilder()
                        .withFullName(fullName)
                        .withBirthDayDate(LocalDate.of(anio, mes, dia))
                        .build());
    }

    @Test
    public void When_one_person_was_already_assigned_other_is_assigned() {
        Worker lucas = workerService.save(
                new WorkerBuilder()
                        .withFullName("Lucas David Traverso")
                        .withBirthDayDate(LocalDate.of(1994, Month.SEPTEMBER, 10))
                        .build());
        Worker virginia = workerService.save(
                new WorkerBuilder()
                        .withFullName("Virginia Carla Traverso")
                        .withBirthDayDate(LocalDate.of(1996, Month.SEPTEMBER, 20))
                        .build());
        Worker margarita = workerService.save(
                new WorkerBuilder()
                        .withFullName("Margarita Elizabeth Silva")
                        .withBirthDayDate(LocalDate.of(1965, Month.SEPTEMBER, 15))
                        .build());
        friendRelationService.save(new FriendRelation(margarita, lucas, 2015));
        friendRelationService.save(new FriendRelation(virginia, margarita, 2015));

        FriendRelation relation = relationEstablisher.buildRelationFor(virginia, 2015);
        assertThat(relation, hasProperty("giftReceiver", equalTo(virginia)));
        assertThat(relation, hasProperty("giftGiver", equalTo(lucas)));
        assertThat(relation, hasProperty("wasFulfilled", equalTo(false)));
//        (relationEstablisher.buildRelationFor(virginia, 2015));
//        assertThat(relation, hasProperty("giftReceiver", equalTo(lucas)));
//        assertThat(relation, hasProperty("giftGiver", equalTo(virginia)));
//        assertThat(relation, hasProperty("wasFulfilled", equalTo(false)));
    }
}