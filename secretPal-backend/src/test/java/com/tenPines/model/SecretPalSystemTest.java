package com.tenPines.model;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.builder.PersonBuilder;
import com.tenPines.persistence.HibernateUtils;
import org.hibernate.cfg.Environment;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class SecretPalSystemTest {

    private MockMvc mockMvc;

    private SecretPalSystem secretPalSystem = new SecretPalSystem();
    private Worker aWorker;

    @Before
    public void setUp() {
        aWorker = new PersonBuilder().build();

        HibernateUtils.addConfiguration(Environment.URL, "jdbc:mysql://localhost/calendardbtest");
        HibernateUtils.addConfiguration(Environment.HBM2DDL_AUTO, "create-drop");
    }

    @Test
    public void When_I_Save_A_New_User_This_Should_Be_Stored() throws Exception {
        secretPalSystem.savePerson(aWorker);

        assertThat(secretPalSystem.retrieveAllPeople(), hasSize(1));
    }

    @Test
    public void When_I_Ask_For_All_The_People_It_Should_Return_Them() throws Exception {
        Worker aWorker = new PersonBuilder().build();
        Worker anotherWorker = new PersonBuilder().build();

        secretPalSystem.savePerson(aWorker);
        secretPalSystem.savePerson(anotherWorker);

        assertThat(secretPalSystem.retrieveAllPeople(),
                hasItems(aWorker, anotherWorker));

    }


    @Test
    public void When_I_Delete_An_Existing_Person_It_Should_Be_No_More() throws Exception {
        Worker aWorker = new PersonBuilder().build();

        secretPalSystem.savePerson(aWorker);
        secretPalSystem.deletePerson(aWorker);

        assertThat(secretPalSystem.retrieveAllPeople(), not(hasItem(aWorker)));
        assertThat(secretPalSystem.retrieveAllPeople(), hasSize(0));
    }

    @Test
    public void When_I_Add_A_User_With_No_Name_I_Should_Get_An_Error() {
        Worker aWorker = new PersonBuilder().build();
        aWorker.setFullName("");

        try {
            secretPalSystem.savePerson(aWorker);
        } catch (ConstraintViolationException e) {
            assertThat(e.getConstraintViolations(), hasSize(1));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Worker", "may not be empty", "fullName")));
        }
    }

    @Test
    public void add_TitleAndDescriptionAreTooLong_ShouldReturnValidationErrorsForTitleAndDescription() {
        Worker aWorker = new Worker(); //completely blank

        try {
            secretPalSystem.savePerson(aWorker);
        } catch (ConstraintViolationException e) {
            assertThat(e.getConstraintViolations(), hasSize(3));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Worker", "may not be empty", "fullName")));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Worker", "may not be empty", "eMail")));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Worker", "may not be null", "birthdayDate")));
        }

    }


    @Test
    public void when_a_worker_wants_to_participate_then_his_intention_changes() {
        secretPalSystem.savePerson(aWorker);
        aWorker.changeParticipationIntention(true);

        secretPalSystem.changeIntention(aWorker);

        assertTrue(secretPalSystem.getWorker(aWorker).getWantsToParticipate());
    }

    @Test
    public void when_a_worker_wants_to_participate_but_he_is_not_save_then_it_should_raise_an_exception() {
        try {
            secretPalSystem.changeIntention(aWorker);
            fail("An exception should raise!");
        }
        catch (RuntimeException e) {
            assertEquals(e.getMessage(), "No value present");
        }

        assertFalse(aWorker.getWantsToParticipate());
    }

}