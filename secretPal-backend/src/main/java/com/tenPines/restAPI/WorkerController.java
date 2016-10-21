package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.application.service.WorkerService;
import com.tenPines.model.User;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private SecretPalSystem system;
    @Autowired
    private WorkerService workerService;

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
        return workerService.save(aWorker);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) throws IOException {
        User user = User.newUser(system.retrieveAWorker(id),"","");
        if ( !user.isAdmin() ) {
            system.deleteAWorker(user.getWorker());
        }
    }

    @RequestMapping(value = "/intention", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void changeIntention(@RequestBody Worker aWorker){
        system.changeIntention(aWorker);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public RestfulException handleException(RestfulException e) {
        return e;
    }
}
