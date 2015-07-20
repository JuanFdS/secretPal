package persistence;

import model.Worker;

import java.util.List;

public interface AbstractRepository<T> {

    List<T> retrieveAll();

    void save(T element);

    T find(T element);
}
