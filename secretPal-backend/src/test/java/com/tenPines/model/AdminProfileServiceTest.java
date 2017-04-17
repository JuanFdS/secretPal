package com.tenPines.model;
import com.tenPines.application.service.AdminService;
import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.UserFactory;
import com.tenPines.builder.WorkerBuilder;
import com.tenPines.model.stubs.RepoAdminStub;
import com.tenPines.model.stubs.RepoUsuariosStub;
import com.tenPines.model.stubs.RepoWorkersStub;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class AdminProfileServiceTest {

    private AdminService adminService;
    private UserService userService;
    private WorkerService workerService;

    @Before
    public void setUp() {
        workerService = new WorkerService(new RepoWorkersStub());
        adminService = new AdminService(workerService, new RepoAdminStub());
        userService = new UserService(new RepoUsuariosStub());
    }

    @Test
    public void whenISaveAnAdminItShouldBeSaved(){
        User user = UserFactory.newUser();
        workerService.save(new WorkerBuilder().withFullName("Test").withEmail("test@test.com").build());
        userService.save(user);

        adminService.save(userService.retrieveUserByUserName("Test"));
        assertThat(adminService.findAdminWorker().get(), is(instanceOf(Worker.class)));
    }

    @Test
    public void whenIDoNotSaveAnAdminItShouldNotBeSaved(){
        assertThat(adminService.findAdminWorker().isPresent(), is(false) );
    }
}
