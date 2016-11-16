package com.tenPines.model;

import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.reglasDeRelaciones.ReglaRegalosReciprocos;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class RelationRuleTest {

    @Test
    public void si_B_no_le_esta_regalando_a_A_entonces_A_le_puede_regalar_a_B() {

        Worker workerA = new WorkerBuilder().build();
        Worker workerB = new WorkerBuilder().build();

        ReglaRegalosReciprocos regla = new ReglaRegalosReciprocos();

        assertTrue(regla.puedeRegalar(workerA,workerB));
    }

    @Test
    public void si_B_le_esta_regalando_a_A_entonces_A_le_no_puede_regalar_a_B() {

        Worker workerA = new WorkerBuilder().build();
        Worker workerB = new WorkerBuilder().build();
        ReglaRegalosReciprocos regla = new ReglaRegalosReciprocos();
        FriendRelation inverseRelation = new FriendRelation(workerB,workerA);

        regla.initializeRelations(inverseRelation);

        assertFalse(regla.puedeRegalar(workerA,workerB));
    }

    @Test
    public void si_A_le_puede_regalar_a_B_ademas_se_debe_guardar_la_relacion() {

        Worker workerA = new WorkerBuilder().build();
        Worker workerB = new WorkerBuilder().build();
        ReglaRegalosReciprocos regla = new ReglaRegalosReciprocos();
        FriendRelation friendRelation = new FriendRelation(workerA,workerB);

        regla.puedeRegalar(workerA,workerB);
        assertTrue(regla.getRelations().contains(friendRelation));
    }
}
