package model;


import org.apache.commons.validator.routines.EmailValidator;

public class PostOffice {

    public void sendMailToWithFriendName(String emailAddress, String palName) {
        checkIfValidEmail(emailAddress);
    }

    private void checkIfValidEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new RuntimeException("The recipient email address is invalid");
    }
}
