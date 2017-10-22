package softuni.repositories.api;

public interface Repository<T> {

    void save(T entity);

    T findById(Long id);
}
