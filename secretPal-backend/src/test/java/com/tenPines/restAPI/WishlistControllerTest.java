package com.tenPines.restAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenPines.application.SecretPalSystem;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.Wish;
import com.tenPines.model.Worker;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
public class WishlistControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SecretPalSystem secretPalSystemMock;

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        Mockito.reset(secretPalSystemMock);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void When_I_Ask_For_All_Wishes_I_Should_Get_Them() throws Exception {
        Worker aWorker = new WorkerBuilder().build();
        Wish aWish = new Wish(aWorker, "Un Pony!");

        when(secretPalSystemMock.retrieveAllWishes()).thenReturn(Arrays.asList(aWish));

        mockMvc.perform(get("/wishlist/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].worker.fullName", is(aWish.getWorker().getFullName())))
                .andExpect(jsonPath("$[0].gift", is(aWish.getGift())));

        verify(secretPalSystemMock, times(1)).retrieveAllWishes();
        verifyNoMoreInteractions(secretPalSystemMock);
    }
}