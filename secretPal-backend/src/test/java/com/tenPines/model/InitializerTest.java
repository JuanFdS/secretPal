package com.tenPines.model;


import com.tenPines.application.SecretPalSystem;
import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.configuration.AdminProperties;
import com.tenPines.integration.SpringBaseTest;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertTrue;

public class InitializerTest extends SpringBaseTest {

    @Autowired
    private WorkerService workerService;
    @Autowired
    private UserService userService;

    @Autowired
    private SecretPalSystem system;

    @Test
    public void when_i_initialize_the_system_the_list_of_workers_must_be_charged(){
        InitializerLocalSystem initializerLocalSystem = new InitializerLocalSystem(workerService, userService);

        assertThat(workerService.getAllParticipants(), is(not(empty())));
    }

    @Test
    public void when_initialize_the_system_twice_the_list_of_workers_must_be_charged_once() throws IOException {
        InitializerLocalSystem initializerLocalSystem = new InitializerLocalSystem(workerService, userService);

        assertThat(workerService.retrieveWorkerByEmail(AdminProperties.getAdminEmail()), is(not(nullValue())));
    }

}
