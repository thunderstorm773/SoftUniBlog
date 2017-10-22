package softuni.repositories.api;

import softuni.entities.Category;
import java.util.List;

public interface CategoryRepository {

    boolean save(Category category);

    List<Category> findAll();

    Category findById(Long id);

    void deleteById(Long id);

    boolean edit(Category editCategory);
}
