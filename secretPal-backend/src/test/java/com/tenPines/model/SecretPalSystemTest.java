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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

public class SecretPalSystemTest {

    private MockMvc mockMvc;

    private SecretPalSystem secretPalSystem = new SecretPalSystem();

    @Before
    public void setUp() {
        HibernateUtils.addConfiguration(Environment.URL, "jdbc:mysql://localhost/calendardbtest");
        HibernateUtils.addConfiguration(Environment.HBM2DDL_AUTO, "create-drop");
    }

    @Test
    public void When_I_Save_A_New_User_This_Should_Be_Stored() throws Exception {
        Person aPerson = new PersonBuilder().build();

        secretPalSystem.savePerson( aPerson );

        assertThat(secretPalSystem.retrieveAllPeople(), hasSize(1));
    }

    @Test
    public void When_I_Ask_For_All_The_People_It_Should_Return_Them() throws Exception {
        Person aPerson = new PersonBuilder().build();
        Person anotherPerson = new PersonBuilder().build();

        secretPalSystem.savePerson(aPerson);
        secretPalSystem.savePerson( anotherPerson );

        assertThat(secretPalSystem.retrieveAllPeople(),
                hasItems(aPerson, anotherPerson));

    }


    @Test
    public void When_I_Delete_An_Existing_Person_It_Should_Be_No_More() throws Exception {
        Person aPerson = new PersonBuilder().build();

        secretPalSystem.savePerson(aPerson);
        secretPalSystem.deletePerson(aPerson);

        assertThat(secretPalSystem.retrieveAllPeople(), not(hasItem(aPerson)));
        assertThat(secretPalSystem.retrieveAllPeople(), hasSize(0));
    }

    @Test
    public void When_I_Add_A_User_With_No_Name_I_Should_Get_An_Error() {
        Person aPerson = new PersonBuilder().build();
        aPerson.setFullName("");

        try {
            secretPalSystem.savePerson(aPerson);
        } catch (ConstraintViolationException e){
            assertThat(e.getConstraintViolations(), hasSize(1));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Person", "may not be empty", "fullName")));
        }
    }

    @Test
    public void add_TitleAndDescriptionAreTooLong_ShouldReturnValidationErrorsForTitleAndDescription() {
        Person aPerson = new Person(); //completely blank

        try {
            secretPalSystem.savePerson(aPerson);
        } catch (ConstraintViolationException e){
            assertThat(e.getConstraintViolations(), hasSize(3));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Person", "may not be empty", "fullName")));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Person", "may not be empty", "eMail")));
            assertThat(e.getMessage(), stringContainsInOrder(Arrays.asList("Validation failed", "Person", "may not be null", "birthdayDate")));
        }

    }

}