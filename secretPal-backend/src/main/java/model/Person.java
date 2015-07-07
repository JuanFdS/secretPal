package model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import java.time.LocalDate;

public class Person {

    private String name;
    private String lastName;
    private String eMail;
    private LocalDate birthdayDate;

    /**
     * Hibernate use only.
     */
    public Person(){}

    public Person(String name, String lastName, String email, LocalDate birthdayDate) {
        checkIfIsValid(name, "Name is invalid");
        checkIfIsValid(lastName, "Last name is invalid");
        checkIfValidEmail(email);
        this.name = name;
        this.lastName = lastName;
        this.eMail = email;
        this.birthdayDate = birthdayDate;
    }

    public String getName() { return name; }

    public String getLastName() { return this.lastName; }

    public String geteMail() { return eMail; }

    public void seteMail(String eMail) { this.eMail = eMail; }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    private void checkIfIsValid(String name, String message) {
        //TODO: Check for names and last name that contains tittles
        checkIfFieldIsValidUponCondition(StringUtils.isBlank(name) || !name.matches("[a-zA-Z ']+"), message);
    }

    private void checkIfValidEmail(String email) {
        checkIfFieldIsValidUponCondition(!EmailValidator.getInstance().isValid(email), "Email is invalid");
    }

    private void checkIfFieldIsValidUponCondition(Boolean condition, String message){
        if(condition) throw new RuntimeException(message);
    }

}
