package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.application.SystemPalFacade;
import com.tenPines.application.service.WorkerService;
import com.tenPines.model.FriendRelation;
//import com.tenPines.model.SecretPalEvent;
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
    private SystemPalFacade systemFacade;

    @Autowired
    private WorkerService workerService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<WorkerWithRelation> workersWithFriends() {
        List<WorkerWithRelation> relations = new ArrayList<>();
        List<Worker> participants = workerService.retrieveParticipants();
        relations.addAll(participants.stream().map(
                participant -> new WorkerWithRelation(participant, systemFacade.retrieveAssignedFriendFor(participant.getId())))
                .collect(Collectors.toList()));
        return relations;

    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void createRelation(@RequestBody @Valid List<FriendRelation> friendRelations) throws IOException, MessagingException {

        for (FriendRelation friendRelation : friendRelations) {

            systemFacade.deleteRelation(friendRelation.getGiftGiver().getId(), friendRelation.getGiftReceiver().getId() );

            systemFacade.createRelation(friendRelation.getGiftGiver(), friendRelation.getGiftReceiver());
        }
    }

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
