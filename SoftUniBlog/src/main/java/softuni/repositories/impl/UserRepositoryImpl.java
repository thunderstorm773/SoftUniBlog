package softuni.repositories.impl;

import softuni.entities.User;
import softuni.enums.Role;
import softuni.repositories.api.UserRepository;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(UserRepository.class)
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(User user) {
        String userEmail = user.getEmail();
        if (!isUserExists(userEmail)) {
            this.entityManager.persist(user);
            return true;
        }

        return false;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        Query query = this.entityManager.createQuery("SELECT u FROM User AS u " +
                "WHERE u.email = :userEmail AND u.password = :userPassword");
        query.setParameter("userEmail", email);
        query.setParameter("userPassword", password);
        try {
            User user = (User) query.getSingleResult();
            return user;
        }catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        return this.entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        Query query = this.entityManager.createQuery("SELECT u FROM User As u");
        List<User> users = (List<User>) query.getResultList();
        return users;
    }

    @Override
    public void deleteById(Long id) {
        User user = this.entityManager.find(User.class, id);
        if (user != null) {
            this.entityManager.remove(user);
        }
    }

    @Override
    public void edit(User editUser) {
        Long userId = editUser.getId();
        String fullName = editUser.getFullName();
        Role role = editUser.getRole();
        String password = editUser.getPassword();

        User user = this.entityManager.find(User.class, userId);
        user.setFullName(fullName);
        user.setRole(role);
        if (password != null) {
            user.setPassword(password);
        }
    }

    private boolean isUserExists(String userEmail) {
        Query query = this.entityManager.createQuery("SELECT u FROM User AS u " +
                "WHERE u.email = :userEmail");
        query.setParameter("userEmail", userEmail);
        try {
            User user = (User) query.getSingleResult();
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }
}
