package com.tenPines.mailer;


import com.tenPines.utils.PropertyParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PostOffice {

    public SMTPPostMan callThePostMan() throws IOException {
        Properties gmailProp = buildPropertyFrom("src/main/resources/gmail.properties");
        Properties mailProp = buildPropertyFrom("src/main/resources/mailTemplate.properties");
        return new SMTPPostMan(gmailProp, mailProp);
    }



    private Properties buildPropertyFrom(String route) throws IOException {
        Properties prop = new PropertyParser();
        prop.load(new FileInputStream(route));
        return prop;
    }

}
