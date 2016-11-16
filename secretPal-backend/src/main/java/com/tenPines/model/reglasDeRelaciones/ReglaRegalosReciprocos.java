package com.tenPines.model.reglasDeRelaciones;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.Worker;

import java.util.ArrayList;

public class ReglaRegalosReciprocos {

    private ArrayList<FriendRelation> relations = new ArrayList<FriendRelation>();

    public boolean puedeRegalar(Worker aWorker, Worker anotherWorker) {
        FriendRelation inverseRelation = new FriendRelation(anotherWorker, aWorker);
        if(!relations.contains(inverseRelation)){
            FriendRelation relation = new FriendRelation(aWorker, anotherWorker);
            relations.add(relation);
            return true;
        }
        return false;
    }

    public void initializeRelations(FriendRelation relation){
        relations.add(relation);
    }
}
