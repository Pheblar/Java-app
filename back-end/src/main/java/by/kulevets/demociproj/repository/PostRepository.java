package by.kulevets.demociproj.repository;

import by.kulevets.demociproj.entity.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Repository
@RequestScope
public interface PostRepository extends JpaRepository<PostModel, Long> {

    PostModel findById(long id);

    @Query(value = "Select * from posts where posts.id not in (:ids)", nativeQuery = true)
    List<PostModel> findByIds(@Param("ids") List<Long> ids);
}
