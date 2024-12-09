package com.devano.blog_app.repository.jpa;

import com.devano.blog_app.entity.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository <Post, Integer>  {
    Optional<Post> findFirstBySlug(String slug);

    List<Post> findAllByIsPublishedAndIsDeleted(Boolean isPublished, Boolean isDeleted);

}
