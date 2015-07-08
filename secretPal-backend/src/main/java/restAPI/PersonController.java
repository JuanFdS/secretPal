package restAPI;

import application.SecretPalSystem;
import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class PersonController {

    @Autowired
    private SecretPalSystem system;

    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> persons(){
        return system.retrieveAllPersons();
    }

    public SecretPalSystem getSystem() {
        return system;
    }

    public void setSystem(SecretPalSystem system) {
        this.system = system;
    }

}
