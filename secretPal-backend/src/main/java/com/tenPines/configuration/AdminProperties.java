package com.tenPines.configuration;

import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;

import java.io.*;

public class AdminProperties {

    private static String adminPropertyFileName = "admin.properties";

    public static String getAdminEmail() throws IOException {
        PropertyParser adminProperty = new PropertyParser(adminPropertyFileName);
        return adminProperty.getProperty("whois");
    }

    public static void setAdmin(Worker newAdmin) throws IOException {
        File f = new File(adminPropertyFileName);
        PropertyParser adminProperty = new  PropertyParser(adminPropertyFileName);

        adminProperty.setProperty("whois", newAdmin.geteMail());

        OutputStream out = new FileOutputStream( f );
        adminProperty.store(out, "Changed via the system");
    }
}
