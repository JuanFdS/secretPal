package com.tenPines.model;

import com.tenPines.application.clock.FakeClock;
import com.tenPines.application.service.FriendRelationService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.integration.SpringBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class RelationsScavengerTest extends SpringBaseTest {
    @Autowired
    private WorkerService workerService;
    @Autowired
    private FriendRelationService friendRelationService;
    private RelationsScavenger relationsScavenger;
    private FakeClock fakeClock;

    private Worker lucas;
    private Worker margarita;
    private Worker virginia;

    @Before
    public void setUp() {
        relationsScavenger = new RelationsScavenger(workerService, friendRelationService);

        lucas = workerService.save(
                new WorkerBuilder()
                        .withFullName("Lucas David Traverso")
                        .withBirthDayDate(LocalDate.of(1994, Month.SEPTEMBER, 10))
                        .build());
        virginia = workerService.save(
                new WorkerBuilder()
                        .withFullName("Virginia Carla Traverso")
                        .withBirthDayDate(LocalDate.of(1996, Month.SEPTEMBER, 20))
                        .build());
        margarita = workerService.save(
                new WorkerBuilder()
                        .withFullName("Margarita Elizabeth Silva")
                        .withBirthDayDate(LocalDate.of(1965, Month.SEPTEMBER, 15))
                        .build());
    }

    @Test
    public void with_no_workers() {
        List<Worker> birthdayWorkers =
                relationsScavenger.findWorkersWithUnassignedBirthdayOn(
                        LocalDate.of(2015, Month.JANUARY, 1));

        assertThat(birthdayWorkers, empty());
    }

    @Test
    public void with_workers_but_no_birthdays() {
        List<Worker> birthdayWorkers =
                relationsScavenger.findWorkersWithUnassignedBirthdayOn(
                        LocalDate.of(2015, Month.JANUARY, 1));

        assertThat(birthdayWorkers, empty());
    }

    @Test
    public void with_a_worker_that_has_a_birthday() {
        List<Worker> birthdayWorkers =
                relationsScavenger.findWorkersWithUnassignedBirthdayOn(
                        LocalDate.of(2015, Month.SEPTEMBER, 10));

        assertThat(birthdayWorkers, hasSize(1));
        assertThat(birthdayWorkers, contains(lucas));
    }

    @Test
    public void with_a_worker_that_has_assignment_for_that_date() {
        friendRelationService.save(new FriendRelation(margarita, lucas, 2015));

        List<Worker> birthdayWorkers =
                relationsScavenger.findWorkersWithUnassignedBirthdayOn(
                        LocalDate.of(2015, Month.SEPTEMBER, 10));

        assertThat(birthdayWorkers, empty());
    }

    @Test
    public void with_a_worker_that_had_an_assignment_last_year() {
        friendRelationService.save(new FriendRelation(margarita, lucas, 2014));

        List<Worker> birthdayWorkers =
                relationsScavenger.findWorkersWithUnassignedBirthdayOn(
                        LocalDate.of(2015, Month.SEPTEMBER, 10));

        assertThat(birthdayWorkers, hasSize(1));
        assertThat(birthdayWorkers, contains(lucas));
    }
}