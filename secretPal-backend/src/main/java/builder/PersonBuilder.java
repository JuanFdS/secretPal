package builder;

import com.github.javafaker.Faker;
import model.Person;

import java.time.LocalDate;
import java.time.Month;


public class PersonBuilder {

    private String name = "Pepe";
    private String lastName = "Grillo";
    private LocalDate birtdayDate = LocalDate.of(1993, Month.APRIL,12);

    public PersonBuilder withName(String name){
        this.name = name;
        return this;
    }

    public PersonBuilder withLastName(String lastName){
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder withBirthDayDate(LocalDate date){
        this.birtdayDate = date;
        return this;
    }

    public Person build(){
        return new Person(name,lastName,birtdayDate);
    }

    public Person buildFromDate(int day, Month month) {
        Faker faker = new Faker();
        return new Person(faker.name().firstName(), faker.name().lastName(), LocalDate.of(1800, month.getValue(), day));
    }
}
