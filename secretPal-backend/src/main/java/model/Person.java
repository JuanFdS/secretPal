package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import configuration.JsonDateDeserializer;
import configuration.JsonDateSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @NotEmpty
    private String name;
    @NotEmpty
    private String lastName;
    @NotEmpty @Email
    private String eMail;
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @NotNull
    private LocalDate birthdayDate;

    public Person(){}

    /* @ManyToMany(mappedBy="participants")
    private Set<SecretPalEvent> secretPalEvents = new HashSet<>();*/

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
