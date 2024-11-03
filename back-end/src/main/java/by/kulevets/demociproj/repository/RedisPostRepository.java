package by.kulevets.demociproj.repository;

import by.kulevets.demociproj.entity.model.CachePostModel;
import by.kulevets.demociproj.entity.model.PostModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

@Repository
@RequestScope
public interface RedisPostRepository extends CrudRepository<CachePostModel, Long> {
}
