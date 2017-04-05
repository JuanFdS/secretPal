package com.tenPines.configuration;

import com.tenPines.application.service.AdminService;
import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

public class AdminProperties {

    public String getAdminEmail(AdminService adminService) throws IOException {
        return adminService.getAdmin().geteMail();
    }

}
