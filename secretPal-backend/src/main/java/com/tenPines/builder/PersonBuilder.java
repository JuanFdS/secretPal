package com.tenPines.builder;

import com.github.javafaker.Faker;
import com.tenPines.model.Worker;

import java.time.LocalDate;
import java.time.Month;


public class PersonBuilder {

    private Faker faker = new Faker();
    private String fullName = faker.name().fullName();
    private String email = faker.internet().emailAddress();
    private LocalDate birthdayDate = LocalDate.of(1993, Month.APRIL,12);

    public PersonBuilder withFullName(String fullName){
        this.fullName = fullName;
        return this;
    }

    public PersonBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public PersonBuilder withBirthDayDate(LocalDate date){
        this.birthdayDate = date;
        return this;
    }

    public Worker build() throws Exception {
        return new Worker(fullName,email,birthdayDate);
    }

    public Worker buildFromDate(int day, Month month) throws Exception {
        return new Worker(faker.name().fullName(),faker.internet().emailAddress(), LocalDate.of(1800, month.getValue(), day));
    }
}
