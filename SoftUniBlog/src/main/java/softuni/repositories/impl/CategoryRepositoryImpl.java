package softuni.repositories.impl;

import softuni.entities.Category;
import softuni.repositories.api.CategoryRepository;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(CategoryRepository.class)
public class CategoryRepositoryImpl implements CategoryRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(Category category) {
        String categoryName = category.getName();
        if (!this.isCategoryExists(categoryName)) {
            this.entityManager.persist(category);
            return true;
        }

        return false;
    }

    @Override
    public List<Category> findAll() {
        Query query = this.entityManager.createQuery("SELECT c FROM Category AS c");
        List<Category> categories = (List<Category>) query.getResultList();
        return categories;
    }

    @Override
    public Category findById(Long id) {
        return this.entityManager.find(Category.class, id);
    }

    @Override
    public void deleteById(Long id) {
        Category category = this.entityManager.find(Category.class, id);
        if (category != null) {
            this.entityManager.remove(category);
        }
    }

    @Override
    public boolean edit(Category editCategory) {
        String editCategoryName = editCategory.getName();
        if (!this.isCategoryExists(editCategoryName)) {
            Long categoryId = editCategory.getId();
            Category category = this.entityManager.find(Category.class, categoryId);
            category.setName(editCategoryName);
            return true;
        }

        return false;

    }

    private boolean isCategoryExists(String categoryName) {
        Query query = this.entityManager.createQuery("SELECT c FROM Category AS c " +
                "WHERE c.name = :categoryName");
        query.setParameter("categoryName", categoryName);
        try {
            Category category = (Category) query.getSingleResult();
            return true;
        } catch (NoResultException nre) {
            return false;
        }
    }
}
