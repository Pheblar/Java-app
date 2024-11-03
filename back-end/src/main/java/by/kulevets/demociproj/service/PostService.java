package by.kulevets.demociproj.service;

import by.kulevets.demociproj.entity.model.CachePostModel;
import by.kulevets.demociproj.entity.model.PostModel;
import by.kulevets.demociproj.entity.pojo.PostPojo;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Service
@RequestScope
public interface PostService {

    void create(PostPojo pojo);
    List<PostModel> getAll();
}
