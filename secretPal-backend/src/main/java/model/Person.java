package model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;

public class Person {

    private String fullName;
    private String eMail;
    private LocalDate dateOfBirth;
    private Boolean wantsToParticipate;

    /**
     * Hibernate use only.

     */
    public Person(){}

    public Person(String fullName, String email, LocalDate dateOfBirth) {
        checkIfIsValid(fullName, "Full name is invalid");
        checkIfValidEmail(email);
        this.fullName = fullName;
        this.eMail = email;
        this.dateOfBirth = dateOfBirth;
        this.wantsToParticipate = false;
    }

    public String emailAdress() { return eMail; }
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String fullName() {
        return fullName;
    }

    public void changeParticipationIntention(Boolean intention) {
        setWantsToParticipate(intention);
    }

    public boolean wantsToParticipate() { return this.wantsToParticipate;}

    public LocalDate dateOfBirth() {
        return dateOfBirth;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private void setDateOfBirth(LocalDate birthdayDate) {
        this.dateOfBirth = birthdayDate;
    }

    private void setWantsToParticipate(Boolean wantsToParticipate) {
        this.wantsToParticipate = wantsToParticipate;
    }

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
