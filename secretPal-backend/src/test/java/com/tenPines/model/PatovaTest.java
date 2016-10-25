package com.tenPines.model;

import com.tenPines.model.process.AssignmentException;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import static com.tenPines.model.process.AssignmentException.Reason.DOES_NOT_WANT_TO_PARTICIPATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class PatovaTest {

    private Patova patova = new PatovaBobo();

    @Test
    public void when_i_authentic_with_any_credential_must_return_userName(){
       Credential credential = Credential.newCredential("kevin", "1234");
       assertThat(patova.enterWith(credential)).isEqualTo(credential.getUserName());
    }

    @Test
    public void when_i_authentic_with_a_invalid_credential_throw_error_of_autentication_and_i_cant_enter(){
        Credential invalidCredential = Credential.newCredential("kevin", "1234");
            try {patova.enterWith(invalidCredential);
                assertTrue("nunca deberia llegar aca", false);
            } catch (RuntimeException ex) {
                assertThat(ex.getMessage()).isEqualTo(PatovaBobo.errorMessageWhenUserIsInvalid());
        }
    }
}
