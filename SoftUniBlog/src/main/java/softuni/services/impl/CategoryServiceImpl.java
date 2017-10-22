package softuni.services.impl;

import softuni.entities.Category;
import softuni.models.bindingModels.AddCategory;
import softuni.models.bindingModels.EditCategory;
import softuni.models.viewModels.ShowCategory;
import softuni.repositories.api.CategoryRepository;
import softuni.services.api.CategoryService;
import softuni.utils.MapperUtil;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
@Local(CategoryService.class)
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;

    @Inject
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean save(AddCategory addCategory) {
        boolean isCategorySaved = false;
        if (addCategory != null) {
            Category category = MapperUtil.getInstance().getModelMapper()
                    .map(addCategory, Category.class);
            isCategorySaved = this.categoryRepository.save(category);
        }

        return isCategorySaved;
    }

    @Override
    public List<ShowCategory> findAll() {
        List<Category> categories = this.categoryRepository.findAll();
        List<ShowCategory> showCategories = MapperUtil.getInstance()
                .convertAll(categories, ShowCategory.class);
        return showCategories;
    }

    @Override
    public ShowCategory findById(Long id) {
        Category category = this.categoryRepository.findById(id);
        ShowCategory showCategory = null;
        if (category != null) {
            showCategory = MapperUtil.getInstance().getModelMapper()
                    .map(category, ShowCategory.class);
        }

        return showCategory;
    }

    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public boolean edit(EditCategory editCategory) {
        boolean isCategoryEdited = false;
        if (editCategory != null) {
            Category category = MapperUtil.getInstance().getModelMapper()
                    .map(editCategory, Category.class);
            isCategoryEdited = this.categoryRepository.edit(category);
        }

        return isCategoryEdited;
    }
}
