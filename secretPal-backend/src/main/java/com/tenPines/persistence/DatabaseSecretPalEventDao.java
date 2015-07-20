package com.tenPines.persistence;

import com.tenPines.model.Person;
import com.tenPines.model.SecretPalEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.nio.channels.SeekableByteChannel;
import java.util.List;

public class DatabaseSecretPalEventDao implements AbstractRepository<SecretPalEvent>  {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try { //SessionFactory from hibernate.cfg.xml
            return new AnnotationConfiguration().configure().buildSessionFactory(); //TODO: Deprecado, pero no lo peudo hacer andar de otra forma :(
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void shutdown(){
        getSessionFactory().close();
    }

    public List save(SecretPalEvent... secretPalEvents) {
        Session session = DatabaseSecretPalEventDao.getSessionFactory().openSession();
        session.beginTransaction();

        for (SecretPalEvent secretPalEvent: secretPalEvents){
            secretPalEvent.getParticipants().forEach(session::save);
            session.save( secretPalEvent );
        }

        session.getTransaction().commit();
        return null;
    }

    @Override
    public List<SecretPalEvent> retrieveAll() {
        return DatabaseSecretPalEventDao.getSessionFactory().openSession().createCriteria( SecretPalEvent.class ).list();
    }

    @Override
    public SecretPalEvent refresh(SecretPalEvent secretPalEvent){
        DatabaseSecretPalEventDao.getSessionFactory().openSession().refresh(secretPalEvent);
        return secretPalEvent;
    }

    @Override
    public void delete(SecretPalEvent secretPalEvent) {
        DatabaseSecretPalEventDao.getSessionFactory().openSession().delete(secretPalEvent);
    }

    @Override
    public SecretPalEvent findById(Long id) {
        return (SecretPalEvent) DatabaseSecretPalEventDao.getSessionFactory().openSession().get(SecretPalEvent.class, id);
    }

}
