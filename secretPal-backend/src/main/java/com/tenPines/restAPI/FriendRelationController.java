package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.application.service.WorkerService;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/friendRelation")
public class FriendRelationController {

    @Autowired
    private SecretPalSystem system;

    @Autowired
    private WorkerService workerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkerWithRelation> workersWithFriends() {
        List<WorkerWithRelation> relations = new ArrayList<>();
        List<Worker> participants = workerService.retrieveParticipants();
        //TODO: esto no funcionaba, hay que ver como hacer la expresión lambda para "Ver Relaciones"
//        relations.addAll(participants.stream().map(
//                participant -> new WorkerWithRelation(participant, system.retrieveAssignedFriendFor(participant)))
//                .collect(Collectors.toList()));
        return relations;

    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void createRelation(@RequestBody @Valid List<FriendRelation> friendRelations, BindingResult result) throws IOException, MessagingException {
        if (result.hasErrors())
            throw new RestfulException(result.getAllErrors());
        SecretPalEvent event = system.retrieveCurrentEvent();

        system.deleteAllRelationsInEvent(event);
        for (FriendRelation friendRelation : friendRelations) {
            system.createRelationInEvent(event, friendRelation.getGiftGiver(), friendRelation.getGiftReceiver());
        }
    }

    @RequestMapping(value = "/{from}/{to}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteRelation(@PathVariable Long from,@PathVariable Long to){
        FriendRelation friendRelation = system.retrieveRelation(from, to);
        SecretPalEvent event = system.retrieveCurrentEvent();
        system.deleteRelationInEvent(friendRelation);
    }

    @RequestMapping(value = "/friend", method = RequestMethod.POST)
    @ResponseBody
    public Worker retrieveGiftee(@RequestBody @Valid Worker loggedWorker, BindingResult result) {
        if (result.hasErrors())
            throw new RestfulException(result.getAllErrors());
        return system.retrieveAssignedFriendFor(loggedWorker);
    }


}
