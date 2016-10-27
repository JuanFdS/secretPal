package com.tenPines.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


public class PatovaTest {

    @Autowired
    private SecurityGuardBobo securityGuardBobo;

    @Test
    public void when_i_authentic_with_any_credential_must_return_userName(){
       Credential credential = Credential.newCredential("kevin", "1234");
       assertThat(securityGuardBobo.enterWith(credential)).isEqualTo(credential.getUserName());
    }

    @Test
    public void when_i_authentic_with_a_invalid_credential_throw_error_of_autentication_and_i_cant_enter(){
        Credential invalidCredential = Credential.newCredential("kevin", "1234");
            try {
                securityGuardBobo.enterWith(invalidCredential);
                assertTrue("nunca deberia llegar aca", false);
            } catch (RuntimeException ex) {
                assertThat(ex.getMessage()).isEqualTo(SecurityGuardBobo.errorMessageWhenUserIsInvalid());
        }
    }
}
