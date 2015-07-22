package com.tenPines.persistence;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DatabaseSecretPalEventDao implements AbstractRepository<SecretPalEvent> {

    private SessionFactory sessionFactory;

    public DatabaseSecretPalEventDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private <T> T transaction(Function<Session, T> function) {
        Session session = this.sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        T ret = function.apply(session);

        transaction.commit();
        return ret;
    }

    public List save(SecretPalEvent... secretPalEvents) {
        Function<Session, List> function = session -> {
            ArrayList<Serializable> idList = new ArrayList<>();
            for (SecretPalEvent secretPalEvent : secretPalEvents) {
                session.save(secretPalEvent);
            }
            return idList;
        };
        return transaction(function);
    }

    public SecretPalEvent retrieveEvent() {
        Function<Session, SecretPalEvent> function = session -> {
            SecretPalEvent event = (SecretPalEvent) session.createCriteria(SecretPalEvent.class).uniqueResult();
            if (event == null) {
                event = new SecretPalEvent();
                session.save(event);
            }
            return event;
        };

        return transaction(function);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SecretPalEvent> retrieveAll() {
        return transaction(session -> {
            return session.createCriteria(SecretPalEvent.class).list();
        });
    }


    @Override
    public SecretPalEvent refresh(SecretPalEvent secretPalEvent) {
        return transaction(session -> {
            session.refresh(secretPalEvent);
            return secretPalEvent;
        });
    }

    @Override
    public void delete(SecretPalEvent secretPalEvent) {
        transaction(session -> {
            session.delete(secretPalEvent);
            return secretPalEvent;
        });
    }

    @Override
    public SecretPalEvent findById(Long id) {
        return transaction((Session session) -> (SecretPalEvent) session.get(SecretPalEvent.class, id));

    }

    @Override
    public void update(SecretPalEvent element) {
        transaction(session -> { session.update(element); return element; } );
    }

    @Override
    public List<SecretPalEvent> retrieveParticipants() { return null;  }

    @Override
    public Worker retrieveAssignedFriendFor(Worker participant) {
        return transaction( session -> { return (Worker) session.createCriteria(FriendRelation.class).
                add(Restrictions.eq("giftGiver.id", participant.getId())).
                setProjection(Projections.property("giftReceiver")).
                uniqueResult(); });
    }

    @Override
    public SecretPalEvent createRelationInEvent(SecretPalEvent event, Worker giftGiver, Worker giftReceiver) {
        transaction(session -> {
            event.registerParticipant(new FriendRelation(giftGiver, giftReceiver));
            session.update(event); return event; }
        );
            return event;

    }

    public void deleteRelationInEvent(SecretPalEvent event, FriendRelation friendRelation) {
        transaction(session -> {
            //event.deleteRelation(friendRelation);
            //session.update(event);
            session.delete(friendRelation);
            return event;
        });
    }

    public FriendRelation retrieveRelation(Long from, Long to) {
        return transaction(
                session -> { return (FriendRelation) session.createCriteria(FriendRelation.class).
                        add(Restrictions.eq("giftGiver.id", from)).
                        add(Restrictions.eq("giftReceiver.id", to)).
                        uniqueResult(); }
        );
    }
}
