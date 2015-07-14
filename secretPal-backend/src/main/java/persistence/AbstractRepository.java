package persistence;

import java.util.List;

public interface AbstractRepository<T> {
    List<T> retrieveAll();
    void save(T... element);
    void refresh(T element);

    T findById(int id);
}
