package com.tenPines.persistence;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class DatabaseSecretPalEventDao extends HibernateGenericDAO<SecretPalEvent> implements SecretPalEventMethods {



    @Override
    protected Class<SecretPalEvent> getDomainClass() {
        return SecretPalEvent.class;
    }

    @Transactional
    public SecretPalEvent retrieveEvent() {
        Session session = getSessionFactory().getCurrentSession();
        List<SecretPalEvent> event = session.createCriteria(SecretPalEvent.class).list();
        if (event.isEmpty()) {
            SecretPalEvent newEvent = new SecretPalEvent();
            session.save(newEvent);
            return newEvent;
        } else {
            return event.get(0);
        }
    }

    @Transactional
    public Worker retrieveAssignedFriendFor(Worker participant) {
        return (Worker) getSessionFactory().getCurrentSession().createCriteria(FriendRelation.class).
                add(Restrictions.eq("giftGiver.id", participant.getId())).
                setProjection(Projections.property("giftReceiver")).
                uniqueResult();
    }

    @Transactional
    public SecretPalEvent createRelationInEvent(SecretPalEvent event, Worker giftGiver, Worker giftReceiver) {
        Session session = getSessionFactory().getCurrentSession();
        FriendRelation relation = new FriendRelation(giftGiver, giftReceiver);
        event.registerParticipant(relation);
        session.save(relation);
        session.update(event);
        return event;
    }

    @Transactional
    public void deleteRelationInEvent(SecretPalEvent event, FriendRelation friendRelation) {
        Session session = getSessionFactory().getCurrentSession();
            event.deleteRelation(friendRelation);
            session.update(event);
            session.delete(friendRelation);
    }

    @Transactional
    public FriendRelation retrieveRelation(Long from, Long to) {
        return (FriendRelation) getSessionFactory().getCurrentSession().createCriteria(FriendRelation.class).
                        add(Restrictions.eq("giftGiver.id", from)).
                        add(Restrictions.eq("giftReceiver.id", to)).
                        uniqueResult();
    }

    @Override
    @Transactional
    public List<FriendRelation> retrieveAllRelations() {
        //TODO: Es medio tonto tener que implementar esto. y surge por no tener un servicio de relaciones.
        return getSessionFactory().getCurrentSession().createCriteria(FriendRelation.class).list();
    }


}
