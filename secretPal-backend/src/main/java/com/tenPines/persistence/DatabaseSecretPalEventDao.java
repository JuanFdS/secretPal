package com.tenPines.persistence;

import com.tenPines.model.FriendRelation;
import com.tenPines.model.SecretPalEvent;
import com.tenPines.model.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
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

    @Override
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
        FriendRelation relation = transaction( session -> { return (FriendRelation) session.createCriteria(FriendRelation.class).
                add(Restrictions.eq("giftGiver.id", participant.getId())).
                uniqueResult(); });
        Worker giftReceiver = null;
        if (relation != null) {
            giftReceiver = relation.getGiftReceiver();
        }
            return giftReceiver;

    }

}
