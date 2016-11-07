package com.tenPines.integration;


import com.tenPines.application.SystemPalFacade;
import com.tenPines.model.GiftDefault;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FacadeTest extends SpringBaseTest {

    @Autowired
    SystemPalFacade facade;

    @Test
    public void when_i_get_all_gift_defaults_and_is_empty(){
        assertThat(facade.retrieveAllGiftsDefaults(), empty());
    }

    @Test
    public void when_i_get_all_gift_defaults_and_have_gift(){
        GiftDefault giftDefault = GiftDefault.createGiftDfault("Mural", "$1000");
        facade.addGiftDefaults(giftDefault);
        assertThat(facade.retrieveAllGiftsDefaults(), not(empty()));
        assertEquals(facade.retrieveAllGiftsDefaults().size(),1);
    }


}
