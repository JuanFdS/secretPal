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
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



public class AdminProfileServiceTest {

    private AdminService adminService;
    private UserService userService;

    @Before
    public void setUp() {
        userService = new UserService();
        adminService = new AdminService();
        User user = UserFactory.newUser();
        userService.userRepository = new RepoUsuariosStub();
        adminService.adminRepository = new RepoAdminStub();
        adminService.workerService = new WorkerService();
        adminService.workerService.workerRepository = new RepoWorkersStub();
        adminService.workerService.save(new WorkerBuilder().withFullName("Test").withEmail("test@test.com").build());
        userService.save(user);
    }

    @Test
    public void whenISaveAnAdminItShouldBeSaved(){
        adminService.save(userService.retrieveUserByUserName("Test"));
        assertThat(adminService.findAdminWorker().get(), is(instanceOf(Worker.class)));
    }

    @Test
    public void whenIDoNotSaveAnAdminItShouldNotBeSaved(){
        adminService.adminRepository.deleteAll();
        assertThat(adminService.findAdminWorker().isPresent(), is(false) );
    }
}
