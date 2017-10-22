package softuni.services.impl;

import softuni.entities.Tag;
import softuni.models.bindingModels.AddTag;
import softuni.models.viewModels.ShowTag;
import softuni.repositories.api.TagRepository;
import softuni.services.api.TagService;
import softuni.utils.MapperUtil;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
@Local(TagService.class)
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    @Inject
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void save(AddTag addTag) {
        if (addTag != null) {
            Tag tag = MapperUtil.getInstance().getModelMapper()
                    .map(addTag, Tag.class);
            this.tagRepository.save(tag);
        }
    }

    @Override
    public void saveAll(List<AddTag> addTags) {
        if (addTags != null) {
            for (AddTag addTag : addTags) {
                this.save(addTag);
            }
        }
    }

    @Override
    public List<ShowTag> findByNames(List<String> tagNames) {
        List<Tag> tags = this.tagRepository.findByNames(tagNames);
        List<ShowTag> showTags = MapperUtil.getInstance()
                .convertAll(tags, ShowTag.class);
        return showTags;
    }

    @Override
    public ShowTag findById(Long id) {
        Tag tag = this.tagRepository.findById(id);
        ShowTag showTag = null;
        if (tag != null) {
            showTag = MapperUtil.getInstance().getModelMapper()
                    .map(tag, ShowTag.class);
        }

        return showTag;
    }
}
