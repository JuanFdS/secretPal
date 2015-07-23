package com.tenPines.persistence;

import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.Worker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:*spring-test-dispatcher-servlet.xml")
@WebAppConfiguration
@Transactional
public class DatabaseWorkerDAOTest {

    private AbstractRepository<Worker> workerDao;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        workerDao = (AbstractRepository<Worker>) webApplicationContext.getBean("databaseWorkerDao");
    }

    @Test
    public void When_I_Have_Zero_Persons_Persisted_When_I_Retrieve_Then_The_List_Is_Empty() {
        assertThat(workerDao.retrieveAll(), empty());
    }

    @Test
    public void When_I_Save_A_New_Person_Then_I_Have_One_More_Person_Persisted() throws Exception {
        Worker aWorker = new WorkerBuilder().build();
        workerDao.save(aWorker);

        List<Worker> result = this.workerDao.retrieveAll();

        assertThat(result, hasSize(1));
        assertThat(result, hasItem(aWorker));
    }

    @Test
    public void When_I_Delete_A_Person_It_Should_Be_No_More() throws Exception {
        Worker aWorker = new WorkerBuilder().build();
        workerDao.save(aWorker);

        workerDao.delete(aWorker);
        List<Worker> result = this.workerDao.retrieveAll();

        assertThat(result, not(hasItem(aWorker)));
    }
}