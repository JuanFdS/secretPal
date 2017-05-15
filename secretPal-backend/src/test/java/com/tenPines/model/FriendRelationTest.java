package com.tenPines.model;

import com.tenPines.builder.WorkerBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.fail;

public class FriendRelationTest {

    private Worker aWorker;
    private Worker otherWorker;

    @Before
    public void setUp(){
        this.aWorker = new WorkerBuilder().build();
        this.otherWorker = new WorkerBuilder().build();
    }
}
