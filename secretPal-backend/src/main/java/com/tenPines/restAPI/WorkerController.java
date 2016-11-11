package com.tenPines.restAPI;

import com.tenPines.application.SystemPalFacade;
import com.tenPines.application.service.WorkerService;
import com.tenPines.model.User;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/worker")
public class WorkerController {

    @Autowired
    private SystemPalFacade system;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private SystemPalFacade systemFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    @ResponseBody
    public List<Worker> workers() {
        List<Worker> workers =system.getAllWorkers();
        return workers;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Worker save(@RequestBody @Valid Worker aWorker) throws IOException {
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

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateWorker(@RequestBody Worker worker) throws Exception {
        systemFacade.editWorker(worker);
    }

}


