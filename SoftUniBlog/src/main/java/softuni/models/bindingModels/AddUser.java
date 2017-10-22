package softuni.models.bindingModels;

import org.hibernate.validator.constraints.Length;
import softuni.enums.Role;
import softuni.staticData.Constants;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class AddUser {

    private String fullName;

    private String email;

    private String password;

    private String confirmPassword;

    private Role role;

    public AddUser() {
        this.role = Role.USER;
    }

    @Length(min = 5, max = 50, message = Constants.REQUIRED_FULL_NAME_LENGTH_MESSAGE)
    @Pattern(regexp = Constants.FULL_NAME_REGEX, message = Constants.ILLEGAL_FULL_NAME_CHARACTERS_MESSAGE)
    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotNull(message = Constants.EMPTY_EMAIL_MESSAGE)
    public String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = Constants.EMPTY_PASSWORD_MESSAGE)
    @Length(min = 3, max = 30, message = Constants.REQUIRED_PASSWORD_LENGTH_MESSAGE)
    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    @NotNull(message = Constants.EMPTY_CONFIRM_PASSWORD_MESSAGE)
    @Length(min = 3, max = 30, message = Constants.REQUIRED_CONFIRM_PASSWORD_LENGTH_MESSAGE)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    private void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
