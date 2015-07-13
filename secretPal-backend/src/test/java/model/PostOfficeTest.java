package model;


import com.github.javafaker.Faker;
import org.junit.Test;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class PostOfficeTest {

    @Test
    public void When_I_try_to_send_a_mail_to_an_invalid_address_an_exception_is_raised() {
        PostOffice aPostOffice = new PostOffice();
        Faker nameFaker = new Faker();
        try {
            aPostOffice.sendMailToWithFriendName("fakeEmail", nameFaker.name().fullName());
            fail("An exception should have been raised");
        } catch(RuntimeException e) {
            assertEquals("The recipient email address is invalid", e.getMessage());
            }
    }

    @Test
    public void When_I_try_to_send_a_mail_with_a_valid_address_the_operation_is_successful(){
        PostOffice aPostOffice = new PostOffice();
        Faker faker = new Faker();
        aPostOffice.sendMailToWithFriendName(faker.internet().emailAddress(), faker.name().fullName());
    }

}
