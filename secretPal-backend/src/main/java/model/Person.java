package model;


import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class Person {

    private String name;
    private String lastName;
    private LocalDate birthdayDate;

    /**
     * Hibernate use only.
     */
    public Person(){}

    public Person(String name, String lastName, LocalDate birthdayDate) {
        checkIfNameIsValid(name);
        this.name = name;
        this.lastName = lastName;
        this.birthdayDate = birthdayDate;
    }

    private void checkIfNameIsValid(String name) {
        if (StringUtils.isBlank(name)) throw new RuntimeException("Name is invalid");
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return this.lastName;
    }

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
}
