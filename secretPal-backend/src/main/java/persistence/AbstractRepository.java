package persistence;

import model.Person;

import java.util.List;

public interface AbstractRepository<T> {
    List<T> retrieveAll();
    void save(T... element);
    void refresh(T element);
    void delete(T element);

    T findById(Long id);

}
