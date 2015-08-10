package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public List<WorkerWithRelation> workersWithFriends() {
        List<WorkerWithRelation> relations = new ArrayList<>();
        List<Worker> participants = system.retrieveParticipants();
        relations.addAll(participants.stream().map(
                participant -> new WorkerWithRelation(participant, system.retrieveAssignedFriendFor(participant)))
                .collect(Collectors.toList()));
        return relations;

    }

    @RequestMapping(value = "/{from}/{to}", method = RequestMethod.POST)
    @ResponseBody
    public void createRelation(@PathVariable Long from,@PathVariable Long to) throws IOException, MessagingException {
        Worker giftGiver = system.retrieveAWorker(from);
        Worker giftReceiver = system.retrieveAWorker(to);
        SecretPalEvent event = system.retrieveCurrentEvent();
        system.createRelationInEvent(event, giftGiver, giftReceiver);
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
