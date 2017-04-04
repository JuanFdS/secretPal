package com.tenPines.model;
import com.tenPines.application.service.AdminService;
import com.tenPines.application.service.UserService;
import com.tenPines.application.service.WorkerService;
import com.tenPines.builder.UserFactory;
import com.tenPines.integration.SpringBaseTest;
import com.tenPines.model.stubs.RepoAdminStub;
import com.tenPines.model.stubs.RepoUsuariosStub;
import com.tenPines.persistence.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.Assert.*;

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
        userService.save(user);
    }

    @Test
    public void whenISaveAnAdminItShouldBeSaved(){
        adminService.save(userService.retrieveUserByUserName("Test"));

        assertNotNull(adminService.getAdmin());
    }
}
