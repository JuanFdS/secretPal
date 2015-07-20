package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private SecretPalSystem system;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Person> persons() {
        return system.retrieveAllPeople();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody @Valid Person aPerson, BindingResult result) throws Exception {
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
    public RestfulException handleException(RestfulException e) {
        return e;
    }

}
