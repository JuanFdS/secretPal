package com.tenPines.model;

import com.tenPines.errors.InvalidGiftDefaultException;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.assertj.core.api.Assertions.assertThat;

public class GiftTest {



    @Test
    public void when_i_create_giftDefault_and_amount_Default_is_null(){
        try {
            GiftDefault.createGiftDfault("Libro", "");
            fail("Should be failed");
        }catch (InvalidGiftDefaultException exception){
            assertThat(exception.getMessage()).isEqualTo(InvalidGiftDefaultException.HAVE_NOT_AMOUNT_DEFAULT);
        }

    }

    @Test
    public void when_i_create_giftDefault_and_gift_Default_is_null(){
        try {
            GiftDefault.createGiftDfault("", "300");
            fail("Should be failed");
        }catch (InvalidGiftDefaultException exception){
            assertThat(exception.getMessage()).isEqualTo(InvalidGiftDefaultException.HAVE_NOT_GIFT_DEFAULT);
        }

    }

}
