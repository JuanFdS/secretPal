package com.tenPines.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tenPines.configuration.JsonDateDeserializer;
import com.tenPines.configuration.JsonDateSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String fullName;
    @NotEmpty
    @Email
    private String eMail;
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @NotNull
    private LocalDate birthdayDate;

    public Person() {
    }

    public Person(String fullName, String email, LocalDate birthdayDate) {
        checkIfIsValid(fullName, "Name is invalid");
        checkIfValidEmail(email);
        this.fullName = fullName;
        this.eMail = email;
        this.birthdayDate = birthdayDate;
    }

    public Long getId() {
        return id;
    }

    /* @ManyToMany(mappedBy="participants")
    private Set<SecretPalEvent> secretPalEvents = new HashSet<>();*/

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    private void checkIfIsValid(String name, String message) {
        checkIfFieldIsValidUponCondition(StringUtils.isBlank(name) || !name.matches("[a-zA-Z '-.]+"), message);
    }

    private void checkIfValidEmail(String email) {
        checkIfFieldIsValidUponCondition(!EmailValidator.getInstance().isValid(email), "Email is invalid");
    }

    private void checkIfFieldIsValidUponCondition(Boolean condition, String message) {
        if (condition) throw new RuntimeException(message);
    }

    @Override
    public boolean equals(Object anObject) {
        if (this == anObject) return true;

        if (!(anObject instanceof Person)) return false;
        Person otherPerson = (Person) anObject;

        return
                this.getFullName().equals(otherPerson.getFullName()) &&
                        this.geteMail().equals(otherPerson.geteMail()) &&
                        this.getBirthdayDate().equals(otherPerson.getBirthdayDate());
    }
}
