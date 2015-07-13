package persistence;

import java.util.List;

public interface AbstractRepository<T> {
    List<T> retrieveAll();
    public void save(T... element);
    public void refresh(T element);
}
