package softuni.services.impl;

import softuni.entities.User;
import softuni.models.bindingModels.AddUser;
import softuni.models.bindingModels.EditUser;
import softuni.models.viewModels.ShowUser;
import softuni.repositories.api.UserRepository;
import softuni.services.api.UserService;
import softuni.utils.MapperUtil;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
@Local(UserService.class)
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean save(AddUser addUser) {
        boolean isUserSaved = false;
        if (addUser != null) {
            User user = MapperUtil.getInstance().getModelMapper()
                    .map(addUser, User.class);
            isUserSaved = this.userRepository.save(user);
            return isUserSaved;
        }

        return isUserSaved;
    }

    @Override
    public ShowUser findByEmailAndPassword(String email, String password) {
        ShowUser showUser = null;
        User user = this.userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            showUser = MapperUtil.getInstance().getModelMapper()
                    .map(user, ShowUser.class);
        }

        return showUser;
    }

    @Override
    public ShowUser findById(Long id) {
        User user = this.userRepository.findById(id);
        ShowUser showUser = null;

        if (user != null) {
            showUser = MapperUtil.getInstance().getModelMapper()
                    .map(user, ShowUser.class);
        }

        return showUser;
    }

    @Override
    public List<ShowUser> findAll() {
        List<User> users = this.userRepository.findAll();
        List<ShowUser> showUsers = MapperUtil.getInstance()
                .convertAll(users, ShowUser.class);
        return showUsers;
    }

    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void edit(EditUser editUser) {
        if (editUser != null) {
            User user = MapperUtil.getInstance().getModelMapper()
                    .map(editUser, User.class);
            this.userRepository.edit(user);
        }
    }
}
