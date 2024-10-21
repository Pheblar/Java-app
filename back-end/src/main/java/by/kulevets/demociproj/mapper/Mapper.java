package by.kulevets.demociproj.mapper;

import by.kulevets.demociproj.entity.model.PostModel;
import by.kulevets.demociproj.entity.pojo.PostPojo;
import org.springframework.stereotype.Component;

@Component
public interface Mapper {

    PostModel toModel(PostPojo pojo);
    PostPojo toPojo(PostModel model);
}
