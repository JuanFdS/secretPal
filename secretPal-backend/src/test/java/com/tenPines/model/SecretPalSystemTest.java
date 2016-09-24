package com.tenPines.model;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.WorkerBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class SecretPalSystemTest {

    private MockMvc mockMvc;

    private SecretPalSystem secretPalSystem;
    private Worker aWorker;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private WorkerService workerService;

    @Before
    public void setUp() throws Exception {
        aWorker = new WorkerBuilder().build();
        secretPalSystem = (SecretPalSystem) webApplicationContext.getBean("secretPalSystem");
    }

    @Test
    public void When_I_Save_A_New_User_This_Should_Be_Stored() throws Exception {
        workerService.save(aWorker);

        assertThat(secretPalSystem.retrieveAllWorkers(), hasSize(1));
    }

    @Test
    public void When_I_Ask_For_All_The_People_It_Should_Return_Them() throws Exception {
        Worker aWorker = new WorkerBuilder().build();
        Worker anotherWorker = new WorkerBuilder().build();

        workerService.save(aWorker);
        workerService.save(anotherWorker);

        assertThat(secretPalSystem.retrieveAllWorkers(),
                hasItems(aWorker, anotherWorker));

    }


    @Test
    public void When_I_Delete_An_Existing_Person_It_Should_Be_No_More() throws Exception {
        Worker aWorker = new WorkerBuilder().build();

        workerService.save(aWorker);
        secretPalSystem.deleteAWorker(aWorker);

        assertThat(secretPalSystem.retrieveAllWorkers(), not(hasItem(aWorker)));
        assertThat(secretPalSystem.retrieveAllWorkers(), hasSize(0));
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


    @Test
    public void when_a_worker_wants_to_participate_then_his_intention_changes() {
        workerService.save(aWorker);

        secretPalSystem.changeIntention(aWorker);

        assertThat(secretPalSystem.getWorker(aWorker).getWantsToParticipate(), is(true));
    }

    @Test
    public void when_a_worker_wants_to_participate_but_he_is_not_save_then_it_should_raise_an_exception() {
        try {
            secretPalSystem.changeIntention(aWorker);
            fail("An exception should raise!");
        }
        catch (RuntimeException e) {
            assertThat(e, hasProperty("message", is("id to load is required for loading")));
        }

        assertThat(aWorker.getWantsToParticipate(), is(false));
    }

}