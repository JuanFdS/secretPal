package com.tenPines.model;


import com.tenPines.application.service.WorkerService;
import com.tenPines.integration.SpringBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

public class InitializerTest extends SpringBaseTest {

    @Autowired
    private WorkerService workerService;

    @Before

    @Test
    public void when_i_initialize_the_system_the_list_of_workers_must_be_charged(){
        InitializerSystem initializerSystem = new InitializerSystem(workerService);

        assertThat(workerService.getAllParticipants(), is(not(empty())));
    }

    @Test
    public void when_initialize_the_system_twice_the_list_of_workers_must_be_charged_once(){
        InitializerSystem initializerSystem = new InitializerSystem(workerService);


        assertThat(workerService.getAllParticipants().size(), is(1));
    }

}
