package softuni.repositories.impl;

import softuni.entities.Tag;
import softuni.repositories.api.TagRepository;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
@Local(TagRepository.class)
public class TagRepositoryImpl implements TagRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Tag tag) {
        if (!isTagExists(tag)) {
            this.entityManager.persist(tag);
        }
    }

    @Override
    public List<Tag> findByNames(List<String> tagNames) {
        Query query = this.entityManager
                .createQuery("SELECT t FROM Tag AS t WHERE t.name IN (:tagNames)");
        query.setParameter("tagNames", tagNames);
        List<Tag> tags = (List<Tag>) query.getResultList();
        return tags;
    }

    @Override
    public Tag findById(Long id) {
        Tag tag = this.entityManager.find(Tag.class, id);
        return tag;
    }

    private boolean isTagExists(Tag tag) {
        Query query = this.entityManager
                .createQuery("SELECT t FROM Tag AS t WHERE t.name = :name");
        query.setParameter("name", tag.getName());
        List<Tag> tags = (List<Tag>) query.getResultList();
        return tags.size() > 0;
    }
}
