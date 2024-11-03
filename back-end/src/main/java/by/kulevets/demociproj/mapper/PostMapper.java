package by.kulevets.demociproj.mapper;

import by.kulevets.demociproj.entity.model.CachePostModel;
import by.kulevets.demociproj.entity.model.PostModel;
import by.kulevets.demociproj.entity.pojo.PostPojo;
import org.springframework.stereotype.Component;

@Component
public class PostMapper implements Mapper{

    @Override
    public PostModel toModel(PostPojo pojo) {
        return PostModel.builder()
                .id(null)
                .title(pojo.getTitle())
                .content(pojo.getContent())
                .author(pojo.getAuthor())
                .build();
    }

    @Override
    public PostPojo toPojo(PostModel model) {
        return PostPojo.builder()
                .title(model.getTitle())
                .content(model.getContent())
                .author(model.getAuthor())
                .build();
    }

    @Override
    public PostModel toModel(CachePostModel cacheModel) {
        return PostModel.builder()
                .id(cacheModel.getId())
                .title(cacheModel.getTitle())
                .author(cacheModel.getAuthor())
                .content(cacheModel.getContent())
                .build();
    }

    @Override
    public CachePostModel toCacheModel(PostModel model) {
        return CachePostModel.builder()
                .id(model.getId())
                .author(model.getAuthor())
                .title(model.getTitle())
                .content(model.getContent())
                .build();
    }
}
