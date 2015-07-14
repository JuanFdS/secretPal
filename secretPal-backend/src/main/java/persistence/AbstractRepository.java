package persistence;

import application.SecretPalSystem;

import java.util.List;

public interface AbstractRepository<T> {
    List<T> retrieveAll();
    public void save(T... element);
    public void refresh(T element);

    T findById(int id);
}
