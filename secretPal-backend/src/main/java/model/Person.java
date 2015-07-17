package model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Person {

    private String fullName;
    private String eMail;
    private LocalDate birthdayDate;
    private Boolean wantsToParticipate;

    /**
     * Hibernate use only.

     */
    public Person(){}

    public Person(String fullName, String email, LocalDate birthdayDate) {
        checkIfIsValid(fullName, "Full name is invalid");
        checkIfValidEmail(email);
        this.fullName = fullName;
        this.eMail = email;
        this.birthdayDate = birthdayDate;
        this.wantsToParticipate = false;
    }

    public String getEMail() { return eMail; }

    public void setEMail(String eMail) {
        checkIfValidEmail(eMail);
        this.eMail = eMail;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }
    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }
    public void setWantsToParticipate(Boolean wantsToParticipate) {
        this.wantsToParticipate = wantsToParticipate;
    }

    public boolean wantsToParticipate() { return this.wantsToParticipate;}

    private void checkIfIsValid(String name, String message) {
        //TODO: Check for names and last name that contains tittles
        checkIfFieldIsValidUponCondition(StringUtils.isBlank(name) || !name.matches("[a-zA-Z ,.'-]+"), message);
    }

    private void checkIfValidEmail(String email) {
        checkIfFieldIsValidUponCondition(!EmailValidator.getInstance().isValid(email), "Email is invalid");
    }

    private void checkIfFieldIsValidUponCondition(Boolean condition, String message){
        if(condition) throw new RuntimeException(message);
    }

}
