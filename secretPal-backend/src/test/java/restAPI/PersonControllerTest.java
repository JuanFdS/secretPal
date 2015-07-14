package restAPI;

import application.SecretPalSystem;
import builder.PersonBuilder;
import builder.TestUtil;
import configuration.ObjectMapperContextResolver;
import model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
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
    private DateTimeFormatter JSONDateFormater = DateTimeFormatter.ofPattern("yyyy-dd-mm");

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
}