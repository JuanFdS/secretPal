package restAPI;

import application.SecretPalSystem;
import builder.PersonBuilder;
import builder.TestUtil;
import model.Person;
import model.SecretPalEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
public class SecretPalEventControllerTest {

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
    public void When_I_Get_Every_SecretPal_Event_I_Should_See_Them_participants() throws Exception {
        Person aPerson = new PersonBuilder().build();
        Person anotherPerson = new PersonBuilder().build();
        SecretPalEvent secretPalEvent = new SecretPalEvent();
        SecretPalEvent anotherSecretPalEvent = new SecretPalEvent();

        secretPalEvent.getParticipants().add(aPerson);
        secretPalEvent.getParticipants().add(anotherPerson);

        //when(secretPalSystemMock.retrieveASecretPalEvent(org.mockito.Mockito.any(int.class))).thenReturn(secretPalEvent);
        when(secretPalSystemMock.retrieveAllSecretPalEvents()).thenReturn(Arrays.asList(secretPalEvent, anotherSecretPalEvent ));

        mockMvc.perform(get("/secretpalevent/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].participants", hasSize(2)))

                .andExpect(jsonPath("$[1].participants", hasSize(0)));

        verify(secretPalSystemMock, times(1)).retrieveAllSecretPalEvents();
        verifyNoMoreInteractions(secretPalSystemMock);
    }
    @Test
    public void When_I_Get_A_SecretPal_Event_I_Should_See_Its_participants() throws Exception {
        Person aPerson = new PersonBuilder().build();
        Person anotherPerson = new PersonBuilder().build();
        SecretPalEvent secretPalEvent = new SecretPalEvent();

        secretPalEvent.getParticipants().add(aPerson);
        secretPalEvent.getParticipants().add(anotherPerson);

        when(secretPalSystemMock.retrieveASecretPalEvent(3)).thenReturn(secretPalEvent);

        mockMvc.perform(get("/secretpalevent/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.participants", hasSize(2)));

        verify(secretPalSystemMock, times(1)).retrieveASecretPalEvent(3);
        verifyNoMoreInteractions(secretPalSystemMock);
    }
}