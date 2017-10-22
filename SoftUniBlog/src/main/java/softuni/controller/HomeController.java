package softuni.controller;

import mvcFramework.annotations.controller.Controller;
import mvcFramework.annotations.parameters.PathVariable;
import mvcFramework.annotations.request.GetMapping;
import mvcFramework.model.Model;
import softuni.models.viewModels.ShowArticle;
import softuni.models.viewModels.ShowCategory;
import softuni.services.api.ArticleService;
import softuni.services.api.CategoryService;
import softuni.staticData.Constants;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
@Controller
public class HomeController {

    private CategoryService categoryService;

    @Inject
    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<ShowCategory> categories = this.categoryService.findAll();
        model.addAttribute(Constants.CATEGORIES_KEY, categories);
        model.addAttribute(Constants.TITLE_KEY, Constants.INDEX_TITLE_VALUE);
        model.addAttribute(Constants.VIEW_KEY, Constants.INDEX_VIEW_VALUE);
        return "base-layout";
    }

    @GetMapping("/category/{id}/articles")
    public String getAllArticlesForCategory(@PathVariable("id") Long id,
                                            Model model) {
        ShowCategory category = this.categoryService.findById(id);
        if (category == null) {
           return "redirect:/index";
        }

        model.addAttribute(Constants.CATEGORY_KEY, category);
        model.addAttribute(Constants.TITLE_KEY, Constants.SEARCH_BY_CATEGORY_TITLE_VALUE);
        model.addAttribute(Constants.VIEW_KEY, Constants.SEARCH_BY_CATEGORY_VIEW_VALUE);
        return "base-layout";
    }
}
