package softuni.models;

import softuni.enums.Role;

public class LoginModel {

    private Long id;

    private Role role;

    public LoginModel(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    private void setRole(Role role) {
        this.role = role;
    }
}
