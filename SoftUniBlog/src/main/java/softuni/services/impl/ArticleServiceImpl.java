package softuni.services.impl;

import softuni.entities.Article;
import softuni.entities.Tag;
import softuni.models.bindingModels.AddArticle;
import softuni.models.bindingModels.EditArticle;
import softuni.models.viewModels.ShowArticle;
import softuni.models.viewModels.ShowTag;
import softuni.repositories.api.ArticleRepository;
import softuni.services.api.ArticleService;
import softuni.staticData.Constants;
import softuni.utils.MapperUtil;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Local(ArticleService.class)
public class ArticleServiceImpl implements ArticleService{

    private ArticleRepository articleRepository;

    @Inject
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public void save(AddArticle addArticle) {
        if (addArticle != null) {
            Article article = MapperUtil.getInstance().getModelMapper()
                    .map(addArticle, Article.class);
            this.articleRepository.save(article);
        }
    }

    @Override
    public List<ShowArticle> findAll() {
        List<Article> articles = this.articleRepository.findAll();
        List<ShowArticle> showArticles = MapperUtil.getInstance()
                .convertAll(articles, ShowArticle.class);

        for (ShowArticle showArticle : showArticles) {
            String content = showArticle.getContent();
            if (content.length() > Constants.ARTICLE_CONTENT_LENGTH_SHOW) {
                int endIndex = Constants.ARTICLE_CONTENT_LENGTH_SHOW - 1;
                content = content.substring(0, endIndex).trim() + "...";
                showArticle.setContent(content);
            }
        }

        return showArticles;
    }

    @Override
    public ShowArticle findById(Long id) {
        Article article = this.articleRepository.findById(id);
        ShowArticle showArticle = null;
        if (article != null) {
            showArticle = MapperUtil.getInstance().getModelMapper()
                    .map(article, ShowArticle.class);
        }

        return showArticle;
    }

    @Override
    public void edit(EditArticle editArticle) {
        if (editArticle != null) {
            Article article = MapperUtil.getInstance().getModelMapper()
                    .map(editArticle, Article.class);
            this.articleRepository.edit(article);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (id != null) {
            this.articleRepository.deleteById(id);
        }
    }

    @Override
    public List<ShowArticle> findByTag(ShowTag showTag) {
        List<ShowArticle> showArticles = new ArrayList<>();
        if (showTag != null) {
            Tag tag = MapperUtil.getInstance().getModelMapper()
                    .map(showTag, Tag.class);
            List<Article> articles = this.articleRepository.findByTag(tag);
            showArticles = MapperUtil.getInstance()
                    .convertAll(articles, ShowArticle.class);
        }

        return showArticles;
    }
}
