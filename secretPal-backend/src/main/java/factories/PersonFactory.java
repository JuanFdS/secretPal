package factories;

import com.github.javafaker.Faker;
import model.Person;

import java.time.LocalDate;
import java.time.Month;

public class PersonFactory {
    public static Person fromDate(int day, Month month) {
        Faker faker = new Faker();
        return new Person(faker.name().firstName(), faker.name().lastName(), LocalDate.of(1800, month.getValue(), day));
    }
}
