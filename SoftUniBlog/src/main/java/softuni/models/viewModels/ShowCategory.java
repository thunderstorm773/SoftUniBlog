package softuni.models.viewModels;

import java.util.List;

public class ShowCategory {

    private Long id;

    private String name;

    private List<ShowArticle> articles;

    public ShowCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ShowArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<ShowArticle> articles) {
        this.articles = articles;
    }
}
