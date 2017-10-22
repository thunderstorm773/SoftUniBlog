package softuni.services.api;

import softuni.models.bindingModels.AddUser;
import softuni.models.bindingModels.EditUser;
import softuni.models.viewModels.ShowUser;

import java.util.List;

public interface UserService {

    boolean save(AddUser addUser);

    ShowUser findByEmailAndPassword(String email, String password);

    ShowUser findById(Long id);

    List<ShowUser> findAll();

    void deleteById(Long id);

    void edit(EditUser editUser);
}
