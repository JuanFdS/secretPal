package persistence;

import java.util.List;

public interface AbstractRepository<T> {

    public List<T> retrieveAll();

    public void save(T element);

}
