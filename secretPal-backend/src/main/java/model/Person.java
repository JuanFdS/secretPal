package model;


import java.time.LocalDate;

public class Person {

    private String name;
    private String lastName;
    private LocalDate birthdayDate;

    public Person(String name, String lastName, LocalDate birthdayDate) {
        this.name = name;
        this.lastName = lastName;
        this.birthdayDate = birthdayDate;
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
}
