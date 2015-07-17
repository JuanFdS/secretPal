package persistence;

import model.Person;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.*;

import java.util.List;

public class DatabasePersonDao implements AbstractRepository<Person>  {

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

    @Override
    public void refresh(Person person){
        DatabaseSecretPalEventDao.getSessionFactory().openSession().refresh(person);
    }

    @Override
    public void delete(Person aPerson) {
        Session session = DatabasePersonDao.getSessionFactory().openSession();
        session.beginTransaction();

            session.delete(aPerson);

        session.getTransaction().commit();
    }

    @Override
    public Person findById(Long id) {
        return (Person) DatabaseSecretPalEventDao.getSessionFactory().openSession().get(Person.class, id);
    }

}
