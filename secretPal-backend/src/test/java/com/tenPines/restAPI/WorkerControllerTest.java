package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.builder.PersonBuilder;
import com.tenPines.model.Worker;
import com.tenPines.utils.TestUtil;
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
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
public class WorkerControllerTest {

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
    public void When_I_Post_A_New_User_This_Should_Be_Stored() throws Exception {
        Worker aWorker = new PersonBuilder().build();

        mockMvc.perform(post("/person/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonStrings(aWorker))
        );

        verify(secretPalSystemMock, times(1)).savePerson(aWorker);

        when(secretPalSystemMock.retrieveAllPeople()).thenReturn(Arrays.asList(aWorker));

        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].fullName", is(aWorker.getFullName())))
                .andExpect(jsonPath("$[0].eMail", is(aWorker.geteMail())));


        verify(secretPalSystemMock, times(1)).retrieveAllPeople();
        verifyNoMoreInteractions(secretPalSystemMock);
    }

    @Test
    public void When_I_GET_all_the_people_it_should_return_them() throws Exception {
        Worker aWorker = new PersonBuilder().build();
        Worker anotherWorker = new PersonBuilder().build();

        when(secretPalSystemMock.retrieveAllPeople()).thenReturn(Arrays.asList(aWorker, anotherWorker));

        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fullName", is(aWorker.getFullName())))
                .andExpect(jsonPath("$[0].eMail", is(aWorker.geteMail())))

                .andExpect(jsonPath("$[1].fullName", is(anotherWorker.getFullName())))
                .andExpect(jsonPath("$[1].eMail", is(anotherWorker.geteMail())));

        verify(secretPalSystemMock, times(1)).retrieveAllPeople();
        verifyNoMoreInteractions(secretPalSystemMock);
    }

    @Test
    public void When_I_Delete_An_Existing_Person_It_Should_Be_No_More() throws Exception {
        Long anId = new Random().nextLong();
        Worker aWorker = new PersonBuilder().build();
        aWorker.setId(anId);

        when(secretPalSystemMock.retrieveAPerson(anId)).thenReturn(aWorker);

        mockMvc.perform(delete("/person/" + anId))
                .andExpect(status().isOk());

        ArgumentCaptor<Worker> formObjectArgument = ArgumentCaptor.forClass(Worker.class);
        verify(secretPalSystemMock, times(1)).deletePerson(formObjectArgument.capture());

        Worker deletedWorker = formObjectArgument.getValue();
        assertThat(deletedWorker, is(aWorker));
    }
}