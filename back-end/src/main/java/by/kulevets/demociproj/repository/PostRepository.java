package by.kulevets.demociproj.repository;

import by.kulevets.demociproj.entity.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

@Repository
@RequestScope
public interface PostRepository extends JpaRepository<PostModel, Long> {
}
