package com.tenPines.integration;


import com.tenPines.application.SystemPalFacade;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class FacadeTest extends SpringBaseTest {

    @Autowired
    SystemPalFacade facade;

    @Test
    public void when_i_get_all_gift_defaults_and_is_empty(){
        assertThat(facade.retrieveAllGiftsDefaults(), empty());
    }

    @Test
    public void when_i_get_all_gift_defaults_and_have_anything(){
       assertThat(facade.retrieveAllGiftsDefaults(), empty());
    }


}
