package softuni.models.bindingModels;

import org.hibernate.validator.constraints.Length;
import softuni.models.viewModels.ShowCategory;
import softuni.models.viewModels.ShowTag;
import softuni.models.viewModels.ShowUser;
import softuni.staticData.Constants;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class AddArticle {

    private String title;

    private String content;

    private ShowUser author;

    private List<ShowTag> tagsList;

    private ShowCategory category;

    public AddArticle() {
        this.tagsList = new ArrayList<>();
    }

    @NotNull(message = Constants.EMPTY_ARTICLE_TITLE_MESSAGE)
    @Length(min = 30, message = Constants.REQUIRED_ARTICLE_TITLE_LENGTH_MESSAGE)
    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = Constants.EMPTY_ARTICLE_CONTENT_MESSAGE)
    @Length(min = 50, message = Constants.REQUIRED_ARTICLE_CONTENT_LENGTH_MESSAGE)
    public String getContent() {
        return content;
    }

    private void setContent(String content) {
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

    @NotNull(message = Constants.EMPTY_CATEGORY_NAME_MESSAGE)
    public ShowCategory getCategory() {
        return category;
    }

    public void setCategory(ShowCategory category) {
        this.category = category;
    }
}
