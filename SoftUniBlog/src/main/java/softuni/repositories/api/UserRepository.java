package softuni.repositories.api;

import softuni.entities.User;

import java.util.List;

public interface UserRepository {

    boolean save(User editUser);

    User findByEmailAndPassword(String email, String password);

    User findById(Long id);

    List<User> findAll();

    void deleteById(Long id);

    void edit(User editUser);
}
