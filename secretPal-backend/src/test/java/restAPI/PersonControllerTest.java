package restAPI;

import application.SecretPalSystem;
import builder.PersonBuilder;
import builder.TestUtil;
import model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SecretPalSystem secretPalSystemMock;

    @Before
    public void setUp() {
        Mockito.reset(secretPalSystemMock);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void When_I_GET_all_the_people_it_should_return_them() throws Exception {
        Person aPerson = new PersonBuilder().build();
        Person anotherPerson = new PersonBuilder().build();
        //No hace falta guardarlos, porque mockeo el como los levanta

        when(secretPalSystemMock.retrieveAllPersons()).thenReturn(Arrays.asList(aPerson, anotherPerson));

        mockMvc.perform(get("/person/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(aPerson.getName())))
                .andExpect(jsonPath("$[0].lastName", is(aPerson.getLastName())))
                .andExpect(jsonPath("$[0].eMail", is(aPerson.geteMail())))

                .andExpect(jsonPath("$[1].name", is(anotherPerson.getName())))
                .andExpect(jsonPath("$[1].lastName", is(anotherPerson.getLastName())))
                .andExpect(jsonPath("$[1].eMail", is(anotherPerson.geteMail())));

        verify(secretPalSystemMock, times(1)).retrieveAllPersons();
        verifyNoMoreInteractions(secretPalSystemMock);
    }

    @Test
    public void When_I_Post_A_New_User_This_Should_Be_Stored() throws Exception {
        Person aPerson = new PersonBuilder().build();

        doNothing().when(secretPalSystemMock).savePerson(org.mockito.Mockito.any(Person.class));

        mockMvc.perform(post("/person/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonStrings(aPerson))
        )
                .andExpect(status().isOk());

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        verify(secretPalSystemMock, times(1)).savePerson(personCaptor.capture());
        verifyNoMoreInteractions(secretPalSystemMock);

        Person sentPerson = personCaptor.getValue();
        assertThat(sentPerson, is(aPerson));
    }

    @Test
    public void When_I_Add_A_User_With_No_Name_I_Should_Get_An_Error() throws Exception {
        mockMvc.perform(post("/person/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lastName\":\"Conn\",\"eMail\":\"dimitri.bahringer@yahoo.com\",\"birthdayDate\":[1993,4,12]}")
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].defaultMessage", is("may not be empty")));
        verifyZeroInteractions(secretPalSystemMock);
    }



    @Test
    public void add_TitleAndDescriptionAreTooLong_ShouldReturnValidationErrorsForTitleAndDescription() throws Exception {
        Person person = new Person(); //completely blank

        mockMvc.perform(post("/person/new")
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonStrings(person))
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.errors", hasSize(4)))
                .andExpect(jsonPath("$.errors[*].field", containsInAnyOrder("name", "lastName", "eMail", "birthdayDate")))
                .andExpect(jsonPath("$.errors[*].defaultMessage", containsInAnyOrder(
                        "may not be null",
                        "may not be empty",
                        "may not be empty",
                        "may not be empty"
                )));

        verifyZeroInteractions(secretPalSystemMock);
    }

}