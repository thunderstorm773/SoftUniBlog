package softuni.services.api;

import softuni.models.bindingModels.AddCategory;
import softuni.models.bindingModels.EditCategory;
import softuni.models.viewModels.ShowCategory;
import java.util.List;

public interface CategoryService {

    boolean save(AddCategory addCategory);

    List<ShowCategory> findAll();

    ShowCategory findById(Long id);

    void deleteById(Long id);

    boolean edit(EditCategory editCategory);
}
