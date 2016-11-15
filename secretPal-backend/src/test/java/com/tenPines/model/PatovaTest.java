package com.tenPines.model;

import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.integration.SpringBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


public class PatovaTest extends SpringBaseTest {

    @Autowired
    private SecurityGuard securityGuard;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private UserService userService;


    @Before
    public void setUp(){
        Worker kevin = new Worker("kevin varela", "kevin.varela@10pines.com", LocalDate.of(1993,11,26), true);
        User userKevin = User.newUser(kevin,"kevin","1234");
        workerService.save(kevin);
        userService.save(userKevin);
    }

    @Test
    public void when_i_authentic_with_any_credential_must_return_userName(){
       Credential credential = Credential.newCredential("kevin", "1234");
       assertThat(securityGuard.enterWith(credential)).isEqualTo(credential.getUserName());
    }

    @Test
    public void when_i_authentic_with_a_invalid_credential_throw_error_of_autentication_and_i_cant_enter(){
        Credential invalidCredential = Credential.newCredential("roberto", "1234");
            try {
                securityGuard.enterWith(invalidCredential);
                assertTrue("nunca deberia llegar aca", false);
            } catch (RuntimeException ex) {
                assertThat(ex.getMessage()).isEqualTo(SecurityGuard.errorMessageWhenUserOrPasswordIsInvalid());
        }
    }
}
