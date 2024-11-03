package by.kulevets.demociproj.mapper;

import by.kulevets.demociproj.entity.model.CachePostModel;
import by.kulevets.demociproj.entity.model.PostModel;
import by.kulevets.demociproj.entity.pojo.PostPojo;
import org.springframework.stereotype.Component;

@Component
public interface Mapper {

    PostModel toModel(PostPojo pojo);
    PostModel toModel(CachePostModel cacheModel);
    PostPojo toPojo(PostModel model);
    CachePostModel toCacheModel(PostModel model);

}
