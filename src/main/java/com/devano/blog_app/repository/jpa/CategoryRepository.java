package com.devano.blog_app.repository.jpa;

import com.devano.blog_app.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {
    Optional<Category> findBySlug(String slug);
    void deleteBySlug(String slug);
}
