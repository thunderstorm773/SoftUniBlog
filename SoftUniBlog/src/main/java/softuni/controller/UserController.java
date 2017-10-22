package softuni.controller;

import mvcFramework.annotations.controller.Controller;
import mvcFramework.annotations.parameters.ModelAttribute;
import mvcFramework.annotations.parameters.RequestParam;
import mvcFramework.annotations.request.GetMapping;
import mvcFramework.annotations.request.PostMapping;
import mvcFramework.model.Model;
import softuni.enums.Role;
import softuni.models.LoginModel;
import softuni.models.bindingModels.AddUser;
import softuni.models.viewModels.ShowUser;
import softuni.services.api.UserService;
import softuni.staticData.Constants;
import softuni.utils.LoggedInUtil;
import softuni.utils.ValidationUtil;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Controller
public class UserController {

    private UserService userService;

    private HttpSession session;

    @Inject
    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @GetMapping("/user/register")
    public String getRegisterPage(Model model) {
        if (LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/index";
        }

        model.addAttribute(Constants.TITLE_KEY, Constants.REGISTER_TITLE_VALUE);
        model.addAttribute(Constants.VIEW_KEY, Constants.REGISTER_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/user/register")
    public String register(@ModelAttribute AddUser addUser, Model model) {
        if (LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/index";
        }

        ValidationUtil<AddUser> validationUtil = new ValidationUtil<>(addUser);
        List<String> errors = validationUtil.getInvalidParamsMessages();

        String password = addUser.getPassword();
        String confirmPassword = addUser.getConfirmPassword();
        if ((password != null && confirmPassword != null) &&
                !password.equals(confirmPassword)) {
            errors.add(Constants.NOT_MATCHING_PASSWORDS_MESSAGE);
        }

        String email = addUser.getEmail();
        if (email != null && !(email.contains(".") && email.contains("@"))) {
            errors.add(Constants.REQUIRED_EMAIL_CHARACTERS_MESSAGE);
        }

        if (!errors.isEmpty()) {
            model.addAttribute(Constants.TITLE_KEY, Constants.REGISTER_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.REGISTER_VIEW_VALUE);
            return "base-layout";
        }

        boolean isUserSaved = this.userService.save(addUser);
        if (!isUserSaved) {
            errors.add(Constants.ALREADY_EXISTS_EMAIL_MESSAGE);
            model.addAttribute(Constants.TITLE_KEY, Constants.REGISTER_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.REGISTER_VIEW_VALUE);
            return "base-layout";
        }

        return "redirect:/user/login";
    }

    @GetMapping("/user/login")
    public String getLoginPage(Model model) {
        if (LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/index";
        }

        model.addAttribute(Constants.TITLE_KEY, Constants.LOGIN_TITLE_VALUE);
        model.addAttribute(Constants.VIEW_KEY, Constants.LOGIN_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/user/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        if (LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/index";
        }

        List<String> errors = new ArrayList<>();
        if (email == null || email.trim().isEmpty()) {
            errors.add(Constants.EMPTY_EMAIL_MESSAGE);
        }

        if (password == null || password.trim().isEmpty()) {
            errors.add(Constants.EMPTY_PASSWORD_MESSAGE);
        }

        if (!errors.isEmpty()) {
            model.addAttribute(Constants.TITLE_KEY, Constants.LOGIN_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.LOGIN_VIEW_VALUE);
            return "base-layout";
        }

        ShowUser user = this.userService.findByEmailAndPassword(email, password);
        if (user == null) {
            errors.add(Constants.INCORRECT_EMAIL_OR_PASSWORD_MESSAGE);
            model.addAttribute(Constants.TITLE_KEY, Constants.LOGIN_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.LOGIN_VIEW_VALUE);
            return "base-layout";
        }

        Long userId = user.getId();
        Role userRole = user.getRole();
        LoginModel loginModel = new LoginModel(userId, userRole);
        this.session.setAttribute(Constants.LOGIN_MODEL_NAME, loginModel);
        return "redirect:/index";
    }

    @GetMapping("/user/logout")
    public String logout() {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/index";
        }

        this.session.invalidate();
        return "redirect:/index";
    }

    @GetMapping("/user/my-profile")
    public String getMyProfilePage(Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        LoginModel loginModel = LoggedInUtil.getLoginModel(this.session);
        Long userId = loginModel.getId();
        ShowUser user = this.userService.findById(userId);
        String email = user.getEmail();
        String fullName = user.getFullName();

        model.addAttribute(Constants.TITLE_KEY, Constants.PROFILE_TITLE_VALUE);
        model.addAttribute(Constants.EMAIL_KEY, email);
        model.addAttribute(Constants.FULL_NAME_KEY, fullName);
        model.addAttribute(Constants.VIEW_KEY, Constants.PROFILE_VIEW_VALUE);
        return "base-layout";
    }
}
