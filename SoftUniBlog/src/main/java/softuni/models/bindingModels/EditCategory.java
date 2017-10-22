package softuni.models.bindingModels;

import org.hibernate.validator.constraints.Length;
import softuni.staticData.Constants;
import javax.validation.constraints.NotNull;

public class EditCategory {

    private Long id;

    private String name;

    public EditCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = Constants.EMPTY_CATEGORY_NAME_MESSAGE)
    @Length(min = 3, max = 50, message = Constants.REQUIRED_CATEGORY_NAME_LENGTH_MESSAGE)
    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
