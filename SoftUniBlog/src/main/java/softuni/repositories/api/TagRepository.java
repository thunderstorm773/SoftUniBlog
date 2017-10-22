package softuni.repositories.api;

import softuni.entities.Tag;
import java.util.List;

public interface TagRepository {

    void save(Tag tag);

    List<Tag> findByNames(List<String> tagNames);

    Tag findById(Long id);
}
