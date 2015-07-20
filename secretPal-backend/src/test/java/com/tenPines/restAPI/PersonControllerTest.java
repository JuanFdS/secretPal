package com.tenPines.restAPI;

import com.tenPines.builder.PersonBuilder;
import com.tenPines.builder.TestUtil;
import com.tenPines.model.Person;
import com.tenPines.persistence.HibernateUtils;
import org.hibernate.cfg.Environment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        HibernateUtils.addConfiguration(Environment.URL, "jdbc:mysql://localhost/calendardbtest");
        HibernateUtils.addConfiguration(Environment.HBM2DDL_AUTO, "create-drop");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void When_I_Post_A_New_User_This_Should_Be_Stored() throws Exception {
        Person aPerson = new PersonBuilder().build();

        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonStrings(aPerson))
        )
                .andExpect(status().isOk());

        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fullName", is(aPerson.getFullName())))
                .andExpect(jsonPath("$[0].birthdayDate", is(aPerson.getBirthdayDate())))
                .andExpect(jsonPath("$[0].eMail", is(aPerson.geteMail())));
    }

    @Test
    public void When_I_GET_all_the_people_it_should_return_them() throws Exception {
        Person aPerson = new PersonBuilder().build();
        Person anotherPerson = new PersonBuilder().build();

        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonStrings(aPerson))
        );
        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonStrings(anotherPerson))
        );

        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fullName", is(aPerson.getFullName())))
                .andExpect(jsonPath("$[0].eMail", is(aPerson.geteMail())))

                .andExpect(jsonPath("$[1].fullName", is(anotherPerson.getFullName())))
                .andExpect(jsonPath("$[1].eMail", is(anotherPerson.geteMail())));

    }

    @Test
    public void When_I_Delete_An_Existing_Person_It_Should_Be_No_More() throws Exception {
        Person aPerson = new PersonBuilder().build();

        mockMvc.perform(delete("/person/" + aPerson.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void When_I_Add_A_User_With_No_Name_I_Should_Get_An_Error() throws Exception {
        Person aPerson = new PersonBuilder().build();
        aPerson.setFullName("");

        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonStrings(aPerson))
        )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("fullName")))
                .andExpect(jsonPath("$.errors[0].defaultMessage", is("may not be empty")));
    }

}