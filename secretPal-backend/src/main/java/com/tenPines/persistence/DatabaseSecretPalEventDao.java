package com.tenPines.persistence;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Transactional
public class DatabaseSecretPalEventDao extends HibernateGenericDAO<SecretPalEvent> implements SecretPalEventMethods {



    @Override
    protected Class<SecretPalEvent> getDomainClass() {
        return SecretPalEvent.class;
    }

    public SecretPalEvent retrieveEvent() {
        Session session = getSessionFactory().getCurrentSession();
        SecretPalEvent event = (SecretPalEvent) session.createCriteria(SecretPalEvent.class).uniqueResult();
        if (event == null) {
            event = new SecretPalEvent();
            session.save(event);
        }
        return event;
    }


    public Worker retrieveAssignedFriendFor(Worker participant) {
        return (Worker) getSessionFactory().getCurrentSession().createCriteria(FriendRelation.class).
                add(Restrictions.eq("giftGiver.id", participant.getId())).
                setProjection(Projections.property("giftReceiver")).
                uniqueResult();
    }
    public SecretPalEvent createRelationInEvent(SecretPalEvent event, Worker giftGiver, Worker giftReceiver) {
        Session session = getSessionFactory().getCurrentSession();
        FriendRelation relation = new FriendRelation(giftGiver, giftReceiver);
        event.registerParticipant(relation);
        session.save(relation);
        session.update(event);
        return event;
    }
    public void deleteRelationInEvent(SecretPalEvent event, FriendRelation friendRelation) {
        Session session = getSessionFactory().getCurrentSession();
            event.deleteRelation(friendRelation);
            session.update(event);
            session.delete(friendRelation);
    }
    public FriendRelation retrieveRelation(Long from, Long to) {
        return (FriendRelation) getSessionFactory().getCurrentSession().createCriteria(FriendRelation.class).
                        add(Restrictions.eq("giftGiver.id", from)).
                        add(Restrictions.eq("giftReceiver.id", to)).
                        uniqueResult();
    }


}
