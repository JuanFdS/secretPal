package com.tenPines.model;

import org.junit.Test;

import java.time.LocalDate;

public class RegisterTest {

    @Test
    public void when_i_register_my_account_must_associate_with_worker(){
        NewUser newUser = NewUser.createANewUser("kevenvarela","1234", "kevin.varela@gmail.com");
    }

}
