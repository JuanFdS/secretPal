package model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private String name;
    private String lastName;
    private String eMail;
    private LocalDate birthdayDate;

    /**
     * Hibernate use only.
     */
    public Person(){}

    @ManyToMany(mappedBy="participants")
    private Set<SecretPalEvent> secretPalEvents = new HashSet<>();

    public Set<SecretPalEvent> getSecretPalEvents() {
        return secretPalEvents;
    }

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

    @Override
    public boolean equals(Object anObject){
        if ( this == anObject ) return true;

        if ( !(anObject instanceof Person) ) return false;
        Person otherPerson = (Person)anObject;

        return
                this.getName().equals(otherPerson.getName()) &&
                this.getLastName().equals(otherPerson.getLastName()) &&
                this.geteMail().equals(otherPerson.geteMail()) &&
                this.getBirthdayDate().equals(otherPerson.getBirthdayDate());
    }

}
