package com.tenPines.model;

import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.reglasDeRelaciones.ReglaRegalosReciprocos;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


public class RelationRuleTest {

    private Worker aWorker;
    private Worker otherWorker;

    @Test
    public void si_B_no_le_esta_regalando_a_A_entonces_A_le_puede_regalar_a_B() {

        Worker workerA = new WorkerBuilder().build();
        Worker workerB = new WorkerBuilder().build();

        ReglaRegalosReciprocos regla = new ReglaRegalosReciprocos();

        assertTrue(regla.asdasdas(workerA,workerB));
    }



}
