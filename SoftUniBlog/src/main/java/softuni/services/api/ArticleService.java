package softuni.services.api;

import softuni.models.bindingModels.AddArticle;
import softuni.models.bindingModels.EditArticle;
import softuni.models.viewModels.ShowArticle;
import softuni.models.viewModels.ShowTag;

import java.util.List;

public interface ArticleService {

    void save(AddArticle addArticle);

    // List all articles and get first 500 symbols of their content
    List<ShowArticle> findAll();

    ShowArticle findById(Long id);

    void edit(EditArticle editArticle);

    void deleteById(Long id);

    List<ShowArticle> findByTag(ShowTag showTag);
}
