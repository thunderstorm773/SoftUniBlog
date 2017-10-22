package softuni.services.api;

import softuni.models.bindingModels.AddTag;
import softuni.models.viewModels.ShowTag;

import java.util.List;

public interface TagService {

    void save(AddTag addTag);

    void saveAll(List<AddTag> tags);

    List<ShowTag> findByNames(List<String> tagNames);

    ShowTag findById(Long id);
}
