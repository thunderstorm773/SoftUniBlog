package softuni.controller;

import mvcFramework.annotations.controller.Controller;
import mvcFramework.annotations.parameters.ModelAttribute;
import mvcFramework.annotations.parameters.PathVariable;
import mvcFramework.annotations.parameters.RequestParam;
import mvcFramework.annotations.request.GetMapping;
import mvcFramework.annotations.request.PostMapping;
import mvcFramework.model.Model;
import softuni.entities.Category;
import softuni.models.LoginModel;
import softuni.models.bindingModels.AddArticle;
import softuni.models.bindingModels.AddTag;
import softuni.models.bindingModels.EditArticle;
import softuni.models.viewModels.ShowArticle;
import softuni.models.viewModels.ShowCategory;
import softuni.models.viewModels.ShowTag;
import softuni.models.viewModels.ShowUser;
import softuni.services.api.ArticleService;
import softuni.services.api.CategoryService;
import softuni.services.api.TagService;
import softuni.services.api.UserService;
import softuni.staticData.Constants;
import softuni.utils.LoggedInUtil;
import softuni.utils.ValidationUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@Controller
public class ArticleController {

    private ArticleService articleService;

    private UserService userService;

    private HttpSession session;

    private TagService tagService;

    private CategoryService categoryService;

    @Inject
    public ArticleController(ArticleService articleService,
                             UserService userService,
                             HttpSession session,
                             TagService tagService,
                             CategoryService categoryService) {
        this.articleService = articleService;
        this.userService = userService;
        this.session = session;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    @GetMapping("/article/create")
    public String getArticleCreatePage(Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        List<ShowCategory> categories = this.categoryService.findAll();
        model.addAttribute(Constants.CATEGORIES_KEY, categories);
        model.addAttribute(Constants.TITLE_KEY, Constants.CREATE_ARTICLE_TITLE_VALUE);
        model.addAttribute(Constants.VIEW_KEY, Constants.CREATE_ARTICLE_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/article/create")
    public String createArticle(@ModelAttribute AddArticle addArticle,
                                Model model, @RequestParam("tagsName") String tagsNameStr,
                                @RequestParam("categoryId") String categoryIdStr) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        if (categoryIdStr == null) {
            return "redirect:/index";
        }

        Long categoryId = Long.valueOf(categoryIdStr);
        ShowCategory showCategory = this.categoryService.findById(categoryId);
        addArticle.setCategory(showCategory);

        ValidationUtil<AddArticle> validationUtil = new ValidationUtil<>(addArticle);
        List<String> errors = validationUtil.getInvalidParamsMessages();

        if (!errors.isEmpty()) {
            List<ShowCategory> categories = this.categoryService.findAll();
            model.addAttribute(Constants.CATEGORIES_KEY, categories);
            model.addAttribute(Constants.TITLE_KEY, Constants.CREATE_ARTICLE_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.CREATE_ARTICLE_VIEW_VALUE);
            return "base-layout";
        }

        Long userId = this.getLoggedInUserId();
        ShowUser author = this.userService.findById(userId);
        addArticle.setAuthor(author);

        if (tagsNameStr != null && !tagsNameStr.isEmpty()) {
            List<String> tagsNameTokens = this.splitTagsNameStr(tagsNameStr);
            this.saveTags(tagsNameTokens);
            List<ShowTag> tags = this.tagService.findByNames(tagsNameTokens);
            addArticle.setTagsList(tags);
        }

        this.articleService.save(addArticle);
        return "redirect:/index";
    }

    @GetMapping("/article/details/{id}")
    public String getArticleDetailsPage(@PathVariable("id") Long id, Model model) {
        ShowArticle article = this.articleService.findById(id);
        if (article == null) {
            return "redirect:/index";
        }

        model.addAttribute(Constants.TITLE_KEY, Constants.ARTICLE_DETAILS_TITLE_VALUE);
        model.addAttribute(Constants.ARTICLE_KEY, article);
        model.addAttribute(Constants.VIEW_KEY, Constants.ARTICLE_DETAILS_VIEW_VALUE);
        return "base-layout";
    }

    @GetMapping("/article/edit/{id}")
    public String getArticleEditPage(@PathVariable("id") Long id, Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        ShowArticle article = this.articleService.findById(id);
        if (article == null) {
            return "redirect:/index";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session) && !LoggedInUtil.isUserArticleAuthor(article, this.session)) {
            return "redirect:/index";
        }

        String tagsStr = this.getTagsNameStr(article);

        List<ShowCategory> categories = this.categoryService.findAll();
        model.addAttribute(Constants.CATEGORIES_KEY, categories);
        model.addAttribute(Constants.TITLE_KEY, Constants.EDIT_ARTICLE_TITLE_VALUE);
        model.addAttribute(Constants.ARTICLE_KEY, article);
        model.addAttribute(Constants.TAGS_STR_KEY, tagsStr);
        model.addAttribute(Constants.VIEW_KEY, Constants.EDIT_ARTICLE_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/article/edit/{id}")
    public String editArticle(@ModelAttribute EditArticle editArticle,
                              @PathVariable("id") Long id, Model model,
                              @RequestParam("tagsName") String tagsNameStr,
                              @RequestParam("categoryId") String categoryIdStr) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        ShowArticle article = this.articleService.findById(id);
        if (article == null) {
            return "redirect:/index";
        }

        if (categoryIdStr == null) {
            return "redirect:/index";
        }

        Long categoryId = Long.valueOf(categoryIdStr);
        ShowCategory showCategory = this.categoryService.findById(categoryId);
        editArticle.setCategory(showCategory);

        ValidationUtil<EditArticle> validationUtil = new ValidationUtil<>(editArticle);
        List<String> errors = validationUtil.getInvalidParamsMessages();
        if (!errors.isEmpty()) {
            model.addAttribute(Constants.TITLE_KEY, Constants.EDIT_ARTICLE_TITLE_VALUE);
            model.addAttribute(Constants.ERRORS_KEY, errors);
            model.addAttribute(Constants.VIEW_KEY, Constants.EDIT_ARTICLE_VIEW_VALUE);
            return "base-layout";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session) && !LoggedInUtil.isUserArticleAuthor(article, this.session)) {
            return "redirect:/index";
        }

        Long articleId = article.getId();
        editArticle.setId(articleId);
        ShowUser author = article.getAuthor();
        editArticle.setAuthor(author);

        if (tagsNameStr != null && !tagsNameStr.isEmpty()) {
            List<String> tagsNameTokens = this.splitTagsNameStr(tagsNameStr);
            this.saveTags(tagsNameTokens);
            List<ShowTag> tags = this.tagService.findByNames(tagsNameTokens);
            editArticle.setTagsList(tags);
        }

        this.articleService.edit(editArticle);
        return "redirect:/index";
    }

    @GetMapping("/article/delete/{id}")
    public String getArticleDeletePage(@PathVariable("id") Long id, Model model) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        ShowArticle article = this.articleService.findById(id);
        if (article == null) {
            return "redirect:/index";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session) && !LoggedInUtil.isUserArticleAuthor(article, this.session)) {
            return "redirect:/index";
        }

        String tagsStr = this.getTagsNameStr(article);

        model.addAttribute(Constants.TITLE_KEY, Constants.DELETE_ARTICLE_TITLE_VALUE);
        model.addAttribute(Constants.ARTICLE_KEY, article);
        model.addAttribute(Constants.TAGS_STR_KEY, tagsStr);
        model.addAttribute(Constants.VIEW_KEY, Constants.DELETE_ARTICLE_VIEW_VALUE);
        return "base-layout";
    }

    @PostMapping("/article/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {
        if (!LoggedInUtil.isUserLoggedIn(this.session)) {
            return "redirect:/user/login";
        }

        ShowArticle article = this.articleService.findById(id);
        if (article == null) {
            return "redirect:/index";
        }

        if (!LoggedInUtil.isUserIsAdmin(this.session) && !LoggedInUtil.isUserArticleAuthor(article, this.session)) {
            return "redirect:/index";
        }

        this.articleService.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/article/search-by-tag/{id}")
    public String getSearchByTagPage(@PathVariable("id") Long id, Model model) {
        ShowTag tag = this.tagService.findById(id);
        if (tag == null) {
            return "redirect:/index";
        }

        List<ShowArticle> articles = this.articleService.findByTag(tag);

        model.addAttribute(Constants.TITLE_KEY, Constants.SEARCH_BY_TAG_TITLE_VALUE);
        model.addAttribute(Constants.TAG_KEY, tag);
        model.addAttribute(Constants.ARTICLES_KEY, articles);
        model.addAttribute(Constants.VIEW_KEY, Constants.SEARCH_BY_TAG_VIEW_VALUE);
        return "base-layout";
    }

    private void saveTags(List<String> tagsNameTokens) {
        List<AddTag> addTags = new ArrayList<>();
        tagsNameTokens.forEach(t -> addTags.add(new AddTag(t)));
        this.tagService.saveAll(addTags);
    }

    private List<String> splitTagsNameStr(String tagsNameStr) {
        return Arrays.stream(tagsNameStr.split(",\\s*"))
                .distinct().collect(Collectors.toList());
    }

    private String getTagsNameStr(ShowArticle article) {
        List<ShowTag> tags = article.getTagsList();
        List<String> tagsName = tags.stream().map(ShowTag::getName)
                .collect(Collectors.toList());
        return String.join(", ", tagsName);
    }

    private Long getLoggedInUserId() {
        LoginModel loginModel = LoggedInUtil.getLoginModel(this.session);
        Long userId = loginModel.getId();
        return userId;
    }
}
