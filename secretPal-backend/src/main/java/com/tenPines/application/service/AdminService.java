package com.tenPines.application.service;

import com.tenPines.model.AdminProfile;
import com.tenPines.model.User;
import com.tenPines.persistence.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    public AdminRepository adminRepository;

    public AdminService() {
    }

    public User save(User aUser) {
        this.adminRepository.save(AdminProfile.newAdmin(aUser));
        return aUser;
    }

    public User getAdmin(){
        AdminProfile adminProfile = adminRepository.findAll().get(0);
        return adminProfile.user;
    }
}
