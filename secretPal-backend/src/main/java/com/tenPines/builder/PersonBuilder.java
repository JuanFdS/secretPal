package com.tenPines.builder;

import com.github.javafaker.Faker;
import com.tenPines.model.Person;

import java.time.LocalDate;
import java.time.Month;


public class PersonBuilder {

    private Faker faker = new Faker();
    private String fullName = faker.name().fullName();
    private String email = faker.internet().emailAddress();
    private LocalDate birthdayDate = LocalDate.of(1993, Month.APRIL, 12);

    public PersonBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public PersonBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public PersonBuilder withBirthDayDate(LocalDate date) {
        this.birthdayDate = date;
        return this;
    }

    public Person build() {
        return new Person(fullName, email, birthdayDate);
    }

    public Person buildFromDate(int day, Month month) {
        this.withBirthDayDate(LocalDate.of(1800, month.getValue(), day));
        return this.build();
    }
}
