package builder;

import com.github.javafaker.Faker;
import model.Person;
import persistence.AbstractRepository;

import java.time.LocalDate;
import java.time.Month;


public class PersonBuilder {

    private Faker faker = new Faker();
    private String name = faker.name().firstName();
    private String lastName = faker.name().lastName();
    private String email = faker.internet().emailAddress();
    private LocalDate birthdayDate = LocalDate.of(1993, Month.APRIL,12);

    public PersonBuilder withName(String name){
        this.name = name;
        return this;
    }

    public PersonBuilder withLastName(String lastName){
        this.lastName = lastName;
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

    public Person build(){
        return new Person(name,lastName,email,birthdayDate);
    }

    public Person buildFromDate(int day, Month month) {
        return new Person(faker.name().firstName(), faker.name().lastName(),faker.internet().emailAddress(), LocalDate.of(1800, month.getValue(), day));
    }
}
