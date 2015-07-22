package com.tenPines.application;

import com.tenPines.model.Wish;
import com.tenPines.model.Worker;
import com.tenPines.persistence.AbstractRepository;
import com.tenPines.persistence.DatabasePersonDao;
import com.tenPines.persistence.HibernateUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SecretPalSystem {

    SessionFactory sessionFactory = HibernateUtils.createSessionFactory();
    private AbstractRepository<Worker> personRepository = new DatabasePersonDao(sessionFactory);

    @Autowired
    private AbstractRepository<Wish> wishRepository;

    public void setWishRepository(AbstractRepository<Wish> wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void saveWorker(Worker newWorker) {
        this.personRepository.save(newWorker);
    }

    public List<Worker> retrieveAllWorkers() {
        return personRepository.retrieveAll();
    }

    public Worker retrieveAWorker(Long id) {
        return personRepository.findById(id);
    }

    public void deleteAWorker(Worker aWorker) {
        personRepository.delete(aWorker);
    }

    public void changeIntention(Worker aWorker) {
        Worker worker = retrieveAWorker(aWorker.getId());
        worker.changeParticipationIntention();
        personRepository.update(worker);
    }

    public Worker getWorker(Worker aWorker) {
        return personRepository.refresh(aWorker);
    }

    public List<Wish> retrieveAllWishes() {
        return wishRepository.retrieveAll();
    }

    public void saveWish(Wish newWish) {
        wishRepository.save(newWish);
    }

    public Wish retrieveAWish(Long id) {
        return wishRepository.findById(id);
    }

    public void deleteAWish(Wish wish) {
        wishRepository.delete(wish);
    }

    public List<Wish> retrievePersonalGiftsFor(Worker worker) {
        return worker.getWishList();
    }

    public void updateWish(Wish wish) {
        wishRepository.update(wish);
    }
}
