package com.tenPines.application;

import com.tenPines.model.Wish;
import com.tenPines.model.Worker;
import com.tenPines.persistence.AbstractRepository;

import java.util.List;

public class SecretPalSystem {

    private AbstractRepository<Worker> workerRepository;

    private AbstractRepository<Wish> wishRepository;

    public void setWishRepository(AbstractRepository<Wish> wishRepository) {
        this.wishRepository = wishRepository;
    }

    public void setWorkerRepository(AbstractRepository<Worker> workerRepository) {
        this.workerRepository = workerRepository;
    }

    public void saveWorker(Worker newWorker) {
        this.workerRepository.save(newWorker);
    }

    public List<Worker> retrieveAllWorkers() {
        return workerRepository.retrieveAll();
    }

    public Worker retrieveAWorker(Long id) {
        return workerRepository.findById(id);
    }

    public void deleteAWorker(Worker aWorker) {
        workerRepository.delete(aWorker);
    }

    public void changeIntention(Worker aWorker) {
        Worker worker = retrieveAWorker(aWorker.getId());
        worker.changeParticipationIntention();
        workerRepository.update(worker);
    }

    public Worker getWorker(Worker aWorker) {
        return workerRepository.refresh(aWorker);
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
