package softuni.repositories.api;

import softuni.entities.Article;
import softuni.entities.Tag;

import java.util.List;

public interface ArticleRepository {

    void save(Article article);

    List<Article> findAll();

    Article findById(Long id);

    void edit(Article article);

    void deleteById(Long id);

    List<Article> findByTag(Tag tag);
}
