package softuni.controller;

import mvcFramework.annotations.controller.Controller;
import mvcFramework.annotations.parameters.ModelAttribute;
import mvcFramework.annotations.parameters.PathVariable;
import mvcFramework.annotations.parameters.RequestParam;
import mvcFramework.annotations.request.GetMapping;
import mvcFramework.annotations.request.PostMapping;
import mvcFramework.model.Model;
import softuni.enums.Role;
import softuni.models.LoginModel;
import softuni.models.bindingModels.AddCategory;
import softuni.models.bindingModels.EditCategory;
import softuni.models.bindingModels.EditUser;
import softuni.models.viewModels.ShowCategory;
import softuni.models.viewModels.ShowUser;
import softuni.services.api.CategoryService;
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
public class AdminController {

    private UserService userService;

    private CategoryService categoryService;

    private HttpSession session;

    @Inject
    public AdminController(UserService userService,
                           CategoryService categoryService,
                           HttpSession session) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.session = session;
    }

    @GetMapping("/admin/users/all")
    public String getAllUsersPage(Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        List<ShowUser> users = this.userService.findAll();
        model.addAttribute(Constants.TITLE_KEY, Constants.ALL_USERS_TITLE_VALUE);
        model.addAttribute(Constants.USERS_KEY, users);
        model.addAttribute(Constants.VIEW_KEY, Constants.ALL_USERS_VIEW_VALUE);
        return "base-layout";
    }

    @GetMapping("/admin/users/delete/{id}")
    public String getDeleteUserPage(@PathVariable("id") Long id, Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        ShowUser user = this.userService.findById(id);
        if (user == null) {
            return "redirect:/index";
        }

        model.addAttribute(Constants.TITLE_KEY, Constants.DELETE_USER_TITLE_VALUE);
        model.addAttribute(Constants.USER_KEY, user);
        model.addAttribute(Constants.VIEW_KEY, Constants.DELETE_USER_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        LoginModel loginModel = LoggedInUtil.getLoginModel(this.session);
        Long loggedInUserId = loginModel.getId();
        if (loggedInUserId.equals(id)) {
            List<String> errors = new ArrayList<>();
            errors.add(Constants.CANNOT_DELETE_YOURSELF_MESSAGE);
            List<ShowUser> users = this.userService.findAll();

            model.addAttribute(Constants.TITLE_KEY, Constants.ALL_USERS_TITLE_VALUE);
            model.addAttribute(Constants.USERS_KEY, users);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.ALL_USERS_VIEW_VALUE);
            return "base-layout";
        }

        this.userService.deleteById(id);
        return "redirect:/admin/users/all";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String getEditUserPage(@PathVariable("id") Long id, Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        ShowUser showUser = this.userService.findById(id);
        if (showUser == null) {
            return "redirect:/admin/users/all";
        }

        model.addAttribute(Constants.TITLE_KEY, Constants.EDIT_USER_TITLE_VALUE);
        model.addAttribute(Constants.USER_KEY, showUser);
        model.addAttribute(Constants.VIEW_KEY, Constants.EDIT_USER_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model,
                           @ModelAttribute EditUser editUser,
                           @RequestParam("roleIndex") String roleIndexStr) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        ShowUser showUser = this.userService.findById(id);
        if (showUser == null) {
            return "redirect:/admin/users/all";
        }

        if (roleIndexStr != null) {
            Integer roleIndex = Integer.valueOf(roleIndexStr);
            Role newRole = Role.values()[roleIndex];
            Long userId = showUser.getId();
            editUser.setId(userId);
            editUser.setRole(newRole);

            String newPassword = editUser.getPassword();
            String newConfirmPassword = editUser.getConfirmPassword();

            if ("".equals(newPassword)) {
                newPassword = null;
                editUser.setPassword(newPassword);
            }

            if ("".equals(newConfirmPassword)) {
                newConfirmPassword = null;
                editUser.setConfirmPassword(newConfirmPassword);

            }

            ValidationUtil<EditUser> validationUtil = new ValidationUtil<>(editUser);
            List<String> errors = validationUtil.getInvalidParamsMessages();

            if ((newPassword != null && newConfirmPassword != null) &&
                    !(newPassword.equals(newConfirmPassword))) {
                errors.add(Constants.NOT_MATCHING_PASSWORDS_MESSAGE);
            }

            if (!errors.isEmpty()) {
                model.addAttribute(Constants.TITLE_KEY, Constants.EDIT_USER_TITLE_VALUE);
                model.addAttribute(Constants.ERRORS_KEY, errors);
                model.addAttribute(Constants.USER_KEY, showUser);
                model.addAttribute(Constants.VIEW_KEY, Constants.EDIT_USER_VIEW_VALUE);
                return "base-layout";
            }

            this.userService.edit(editUser);
        }

        return "redirect:/admin/users/all";
    }

    @GetMapping("/admin/categories/all")
    public String getAllCategoriesPage(Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        List<ShowCategory> categories = this.categoryService.findAll();

        model.addAttribute(Constants.TITLE_KEY, Constants.ALL_CATEGORIES_TITLE_VALUE);
        model.addAttribute(Constants.CATEGORIES_KEY, categories);
        model.addAttribute(Constants.VIEW_KEY, Constants.ALL_CATEGORIES_VIEW_VALUE);
        return "base-layout";
    }

    @GetMapping("/admin/category/create")
    public String getCreateCategoryPage(Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        model.addAttribute(Constants.TITLE_KEY, Constants.CREATE_CATEGORY_TITLE_VALUE);
        model.addAttribute(Constants.VIEW_KEY, Constants.CREATE_CATEGORY_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/admin/category/create")
    public String createCategory(@ModelAttribute AddCategory addCategory,
                                 Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        ValidationUtil<AddCategory> validationUtil = new ValidationUtil<>(addCategory);
        List<String> errors = validationUtil.getInvalidParamsMessages();
        if (!errors.isEmpty()) {
            model.addAttribute(Constants.TITLE_KEY, Constants.CREATE_CATEGORY_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.CREATE_CATEGORY_VIEW_VALUE);
            return "base-layout";
        }

        boolean isCategorySaved = this.categoryService.save(addCategory);
        if (!isCategorySaved) {
            errors.add(Constants.ALREADY_EXISTS_CATEGORY_MESSAGE);
            model.addAttribute(Constants.TITLE_KEY, Constants.CREATE_CATEGORY_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.CREATE_CATEGORY_VIEW_VALUE);
            return "base-layout";
        }

        return "redirect:/admin/categories/all";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String getDeleteCategoryPage(@PathVariable("id") Long id,
                                        Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        ShowCategory showCategory = this.categoryService.findById(id);
        if (showCategory == null) {
            return "redirect:/admin/categories/all";
        }

        model.addAttribute(Constants.TITLE_KEY, Constants.DELETE_CATEGORY_TITLE_VALUE);
        model.addAttribute(Constants.CATEGORY_KEY, showCategory);
        model.addAttribute(Constants.VIEW_KEY, Constants.DELETE_CATEGORY_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        this.categoryService.deleteById(id);
        return "redirect:/admin/categories/all";
    }

    @GetMapping("/admin/categories/edit/{id}")
    public String getEditCategoryPage(@PathVariable("id") Long id, Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        ShowCategory showCategory = this.categoryService.findById(id);
        if (showCategory == null) {
            return "redirect:/admin/categories/all";
        }

        model.addAttribute(Constants.TITLE_KEY, Constants.EDIT_CATEGORY_TITLE_VALUE);
        model.addAttribute(Constants.CATEGORY_KEY, showCategory);
        model.addAttribute(Constants.VIEW_KEY, Constants.EDIT_CATEGORY_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/admin/categories/edit/{id}")
    public String editCategory(@PathVariable("id") Long id,
                               @ModelAttribute EditCategory editCategory,
                               Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session)) {
            return "redirect:/index";
        }

        ShowCategory showCategory = this.categoryService.findById(id);
        if (showCategory == null) {
            return "redirect:/admin/categories/all";
        }

        ValidationUtil<EditCategory> validationUtil = new ValidationUtil<>(editCategory);
        List<String> errors = validationUtil.getInvalidParamsMessages();
        if (!errors.isEmpty()) {
            model.addAttribute(Constants.TITLE_KEY, Constants.EDIT_CATEGORY_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.EDIT_CATEGORY_VIEW_VALUE);
            return "base-layout";
        }

        Long categoryId = showCategory.getId();
        editCategory.setId(categoryId);
        boolean isCategoryEdited = this.categoryService.edit(editCategory);
        if (!isCategoryEdited) {
            errors.add(Constants.ALREADY_EXISTS_CATEGORY_MESSAGE);
            model.addAttribute(Constants.TITLE_KEY, Constants.EDIT_CATEGORY_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.EDIT_CATEGORY_VIEW_VALUE);
            return "base-layout";
        }

        return "redirect:/admin/categories/all";
    }
}
