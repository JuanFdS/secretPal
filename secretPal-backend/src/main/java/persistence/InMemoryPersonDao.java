package persistence;

import model.Worker;

import java.util.ArrayList;
import java.util.List;


public class InMemoryPersonDao implements AbstractRepository<Worker> {

    protected static List<Worker> workers = new ArrayList<Worker>();

    public static List<Worker> getWorkers() {
        return workers;
    }

    public static void setWorkers(List<Worker> workers) {
        InMemoryPersonDao.workers = workers;
    }

    @Override
    public List<Worker> retrieveAll() {
        return workers;
    }

    @Override
    public void save(Worker element) {
        workers.add(element);
    }

    @Override
    public Worker find(Worker element) {
        return getWorkers().stream().filter( (Worker aWorker) -> element.getEMailAdress().equals(aWorker.getEMailAdress())).findFirst().get();
    }


}
