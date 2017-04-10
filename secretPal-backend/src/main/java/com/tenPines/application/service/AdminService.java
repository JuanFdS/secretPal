package com.tenPines.application.service;

import com.tenPines.model.AdminProfile;
import com.tenPines.model.User;
import com.tenPines.model.Worker;
import com.tenPines.persistence.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    public AdminRepository adminRepository;
    @Autowired
    public WorkerService workerService;

    public AdminService() {

    }

    public User save(User aUser) {
        this.adminRepository.save(AdminProfile.newAdmin(aUser));
        return aUser;
    }

    public Optional<Worker> findAdminWorker(){
        return workerService.retrieveWorkerByEmail(findAdminUser().orElse(nullUser()).geteMail());
    }

    public Optional<User> findAdminUser() {
        Optional<AdminProfile> adminProfile = adminRepository.findAll().stream().findFirst();
        return adminProfile.map(a->a.user);
    }

    public User nullUser(){
        return User.newUser(new Worker(),"","");
    }

    public boolean isAdmin(User user){
       if(!findAdminUser().isPresent())
        {return false;}
       return this.findAdminUser().get().getId().equals(user.getId());
    }
}
