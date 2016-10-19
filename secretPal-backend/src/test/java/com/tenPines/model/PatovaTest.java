package com.tenPines.model;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Kevin on 19/10/16.
 */
public class PatovaTest {

    private Patova patova = new Patova();  //TODO AUTOWIREAR

    @Test
    public void ZZZZZZ(){
       Credential credential = new Credential();
       assertThat(patova.canEntry(credential)).isTrue();
    }

}
