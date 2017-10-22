package softuni.models.viewModels;

import java.util.List;

public class ShowArticle {

    private Long id;

    private String title;

    private String content;

    private ShowUser author;

    private List<ShowTag> tagsList;

    private ShowCategory category;

    public ShowArticle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ShowUser getAuthor() {
        return author;
    }

    public void setAuthor(ShowUser author) {
        this.author = author;
    }

    public List<ShowTag> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<ShowTag> tagsList) {
        this.tagsList = tagsList;
    }

    public ShowCategory getCategory() {
        return category;
    }

    public void setCategory(ShowCategory category) {
        this.category = category;
    }
}
