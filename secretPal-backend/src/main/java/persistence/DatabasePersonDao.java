package persistence;

import model.Person;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.*;

import java.util.List;

public class DatabasePersonDao implements AbstractRepository<Person>  {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try { //SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
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

    @Override
    public void save(Person... people) {
        Session session = DatabasePersonDao.getSessionFactory().openSession();
        session.beginTransaction();

        for (Person person : people){
            session.save( person );
        }

        session.getTransaction().commit();
    }

    @Override
    public List<Person> retrieveAll() {
        return DatabasePersonDao.getSessionFactory().openSession().createCriteria( Person.class ).list();
    }

}
