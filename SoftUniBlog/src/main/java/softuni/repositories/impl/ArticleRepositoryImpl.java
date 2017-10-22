package softuni.repositories.impl;

import softuni.entities.Article;
import softuni.entities.Category;
import softuni.entities.Tag;
import softuni.repositories.api.ArticleRepository;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

@Stateless
@Local(ArticleRepository.class)
public class ArticleRepositoryImpl implements ArticleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Article article) {
        this.entityManager.persist(article);
    }

    @Override
    public List<Article> findAll() {
        Query query = this.entityManager.createQuery("SELECT a FROM Article AS a");
        List<Article> articles = (List<Article>) query.getResultList();
        return articles;
    }

    @Override
    public Article findById(Long id) {
        return this.entityManager.find(Article.class, id);
    }

    @Override
    public void edit(Article editArticle) {
        Long articleId = editArticle.getId();
        String title = editArticle.getTitle();
        String content = editArticle.getContent();
        Category category = editArticle.getCategory();
        Set<Tag> tagsList = editArticle.getTagsList();

        Article article = this.entityManager.find(Article.class, articleId);
        article.setTitle(title);
        article.setContent(content);
        article.setCategory(category);
        article.setTagsList(tagsList);
    }

    @Override
    public void deleteById(Long id) {
        Article article = this.entityManager.find(Article.class, id);
        this.entityManager.remove(article);
    }

    @Override
    public List<Article> findByTag(Tag tag) {
        Long tagId = tag.getId();
        Query query = this.entityManager
                .createQuery("SELECT a FROM Article AS a INNER JOIN a.tagsList " +
                        "AS t WHERE t.id = :tagId");
        query.setParameter("tagId", tagId);
        List<Article> articles = (List<Article>) query.getResultList();
        return articles;
    }
}
