package com.tenPines.model;

import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.integration.SpringBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WorkerServiceTest extends SpringBaseTest{

    private Worker aWorker;

    @Autowired
    private WorkerService workerService;

    @Before
    public void setUp() throws Exception {
        aWorker = new WorkerBuilder().build();
    }

    @Test
    public void When_I_Save_A_New_User_This_Should_Be_Stored() throws Exception {
        workerService.save(aWorker);

        assertThat(workerService.getAllParticipants(), hasSize(1));
    }

    @Test
    public void When_I_Ask_For_All_The_People_It_Should_Return_Them() throws Exception {
        Worker aWorker = new WorkerBuilder().build();
        Worker anotherWorker = new WorkerBuilder().build();

        workerService.save(aWorker);
        workerService.save(anotherWorker);

        assertThat(workerService.getAllParticipants(),
                hasItems(aWorker, anotherWorker));

    }


    @Test
    public void When_I_Delete_An_Existing_Person_It_Should_Be_No_More() throws Exception {
        Worker aWorker = new WorkerBuilder().build();

        workerService.save(aWorker);
        workerService.remove(aWorker);

        assertThat(workerService.getAllParticipants(), not(hasItem(aWorker)));
        assertThat(workerService.getAllParticipants(), hasSize(0));
    }

    @Test
    public void When_I_Add_A_User_With_No_Name_I_Should_Get_An_Error() throws Exception {
        Worker aWorker = new WorkerBuilder().build();
        aWorker.setFullName("");

        try {
            workerService.save(aWorker);
        } catch (ConstraintViolationException e) {
            assertThat(e.getConstraintViolations(), hasSize(1));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Worker", "may not be empty", "fullName")));
        }
    }

    @Test
    public void When_I_Add_A_Blank_Worker_I_Should_Get_A_Ton_of_Errors() {
        Worker aWorker = new Worker(); //completely blank

        try {
            workerService.save(aWorker);
        } catch (ConstraintViolationException e) {
            assertThat(e.getConstraintViolations(), hasSize(4));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Worker", "may not be empty", "fullName")));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Worker", "may not be null", "dateOfBirth")));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Worker", "may not be empty", "eMail")));
        }

    }

}