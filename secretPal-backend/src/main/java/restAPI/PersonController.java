package restAPI;

import application.SecretPalSystem;
import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private SecretPalSystem system;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> persons(){
        return system.retrieveAllPersons();
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void save(@RequestBody Person aPerson){
        system.savePerson(aPerson);
    }


    public SecretPalSystem getSystem() { return system; }

    public void setSystem(SecretPalSystem system) {
        this.system = system;
    }

}
