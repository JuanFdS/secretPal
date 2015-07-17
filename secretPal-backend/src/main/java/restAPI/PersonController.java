package restAPI;

import application.SecretPalSystem;
import builder.TestUtil;
import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private SecretPalSystem system;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> persons(){
        return system.retrieveAllPersons();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody @Valid Person aPerson, BindingResult result, Model m) throws Exception {
        if (result.hasErrors())
            throw new RestfulException(result.getAllErrors());
        system.savePerson(aPerson);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        Person aPerson = system.retrieveAPerson(id);
        system.deletePerson(aPerson);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RestfulException handleException(RestfulException e){
        return e;
    }

}
