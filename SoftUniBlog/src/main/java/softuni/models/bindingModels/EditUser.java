package softuni.models.bindingModels;

import org.hibernate.validator.constraints.Length;
import softuni.enums.Role;
import softuni.staticData.Constants;
import javax.validation.constraints.Pattern;

public class EditUser {

    private Long id;

    private String fullName;

    private String password;

    private String confirmPassword;

    private Role role;

    public EditUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Length(min = 5, max = 50, message = Constants.REQUIRED_FULL_NAME_LENGTH_MESSAGE)
    @Pattern(regexp = Constants.FULL_NAME_REGEX, message = Constants.ILLEGAL_FULL_NAME_CHARACTERS_MESSAGE)
    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Length(min = 3, max = 30, message = Constants.REQUIRED_PASSWORD_LENGTH_MESSAGE)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 3, max = 30, message = Constants.REQUIRED_CONFIRM_PASSWORD_LENGTH_MESSAGE)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
