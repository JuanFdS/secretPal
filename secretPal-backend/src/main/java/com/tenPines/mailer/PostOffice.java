package com.tenPines.mailer;


import java.io.IOException;

public class PostOffice {

    public SMTPPostMan callThePostMan() throws IOException {
        return new SMTPPostMan();
    }

}
