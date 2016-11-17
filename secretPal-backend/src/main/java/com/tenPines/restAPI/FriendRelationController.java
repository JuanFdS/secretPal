package com.tenPines.restAPI;

import com.tenPines.application.SystemPalFacade;
import com.tenPines.application.service.WorkerService;
import com.tenPines.model.FriendRelation;
//import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/friendRelation")
public class FriendRelationController {


    @Autowired
    private SystemPalFacade systemFacade;

    @Autowired
    private WorkerService workerService;

    //GUARDAR RELACION MANUAL CREADA EN EL FRONTEND
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void createRelation(@RequestBody @Valid List<FriendRelation> friendRelations) throws IOException, MessagingException {
    systemFacade.deleteAllRelations();
    friendRelations.forEach(friendRelation -> systemFacade.createRelation(friendRelation.getGiftGiver(), friendRelation.getGiftReceiver()));
    }

    // INICIAR ASIGNACION DE PINOS X DEFAULT
    @RequestMapping(value = "/initilizeRelations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<FriendRelation> createRelations(){
        return systemFacade.initializeRelations();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<FriendRelation> workersWithFriends() {
        List<FriendRelation> relations = systemFacade.retrieveAllRelations();
        return relations;
    }

    @RequestMapping(value = "/posibleFriend/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<Worker> posiblesFriends(@PathVariable Long id) {
        return systemFacade.getPosibleFriendsTo(id);
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    public void createRelation(@RequestBody @Valid List<FriendRelation> friendRelations) throws IOException, MessagingException {
//
//        systemFacade.deleteAllRelations();
//        friendRelations.forEach(friendRelation -> systemFacade.createRelation(friendRelation.getGiftGiver(), friendRelation.getGiftReceiver()));
//    }

    @RequestMapping(value = "/{from}/{to}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteRelation(@PathVariable Long from,@PathVariable Long to){
        systemFacade.deleteRelation(from,to);

    }

    @RequestMapping(value = "/friend/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Worker retrieveGiftee(@PathVariable("id") Long id) {
        return systemFacade.retrieveAssignedFriendFor(id);
    }

}
