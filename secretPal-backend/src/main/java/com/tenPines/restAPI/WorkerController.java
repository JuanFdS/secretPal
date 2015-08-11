package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.Worker;
import com.tenPines.utils.AuthUtils;
import com.tenPines.utils.PropertyParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private SecretPalSystem system;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<Worker> workers() {
        return system.retrieveAllWorkers();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Worker save(@RequestHeader(value="Authorization") String header, @RequestBody @Valid Worker aWorker, BindingResult result) throws Exception {
        if (result.hasErrors())
            throw new RestfulException(result.getAllErrors());
        return system.saveWorker(aWorker);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) throws IOException {
        Worker aWorker = system.retrieveAWorker(id);
        if (!isAdmin(aWorker)) {
            system.deleteAWorker(aWorker);
        }
    }

    @RequestMapping(value = "/intention", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    @ResponseStatus(value = HttpStatus.OK)
    public void changeIntention(@RequestBody Worker aWorker){
        system.changeIntention(aWorker);
    }

    @RequestMapping(value = "/adminMail", method = RequestMethod.GET)
    @ResponseBody
    public String adminMail() throws IOException {
        PropertyParser adminProperty = new PropertyParser("src/main/resources/admin.properties");
        return adminProperty.getProperty("whois.admin");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RestfulException handleException(RestfulException e) {
        return e;
    }

    private boolean isAdmin(Worker aWorker) throws IOException {
        PropertyParser adminProperty = new PropertyParser("src/main/resources/admin.properties");
        return aWorker.geteMail().equals(adminProperty.getProperty("whois.admin"));
    }
}
