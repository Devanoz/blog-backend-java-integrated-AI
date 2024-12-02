package com.devano.blog_app.repository;

import com.devano.blog_app.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

    Page<Comment> findAllByPost_Slug(String postSlug, Pageable pageable);
}
