package com.tenPines.configuration;

import com.tenPines.model.Worker;
import com.tenPines.utils.PropertyParser;

import java.io.*;

public class AdminProperties {

    private static String adminPropertyRoute = "src/main/resources/admin.properties";

    public static String getAdminEmail() throws IOException {
        PropertyParser adminProperty = new PropertyParser(adminPropertyRoute);
        return adminProperty.getProperty("whois");
    }

    public static void setAdmin(Worker newAdmin) throws IOException {
        File f = new File(adminPropertyRoute );
        OutputStream out = new FileOutputStream( f );
        PropertyParser adminProperty = new PropertyParser(adminPropertyRoute);
        adminProperty.store(out, "Changed via the system");
    }
}
