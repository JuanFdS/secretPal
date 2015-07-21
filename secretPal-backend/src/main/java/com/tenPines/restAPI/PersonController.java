package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
    public List<Worker> persons() {
        return system.retrieveAllWorkers();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public void save(@RequestBody @Valid Worker aWorker, BindingResult result) throws Exception {
        if (result.hasErrors())
            throw new RestfulException(result.getAllErrors());
        system.saveWorker(aWorker);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Long id) {
        Worker aWorker = system.retrieveAWorker(id);
        system.deleteAWorker(aWorker);
    }

    @RequestMapping(value = "/intention", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void changeIntention(@RequestBody Worker aWorker){
        system.changeIntention(aWorker);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RestfulException handleException(RestfulException e) {
        return e;
    }

}
