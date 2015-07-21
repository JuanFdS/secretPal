package com.tenPines.restAPI;

import com.tenPines.application.SecretPalSystem;
import com.tenPines.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.Expression;
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
}
