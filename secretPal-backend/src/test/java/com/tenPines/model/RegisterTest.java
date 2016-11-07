package com.tenPines.model;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class RegisterTest {

    @Test
    public void when_i_register_my_account_with_username_and_password_not_will_be_empty(){

        try {
            NewUser newUser = NewUser.createANewUser("","", "kevin.varela@10pines.com");
            fail("The exception was not raised");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Any field is null, please check your input data"));
        }
    }
}
