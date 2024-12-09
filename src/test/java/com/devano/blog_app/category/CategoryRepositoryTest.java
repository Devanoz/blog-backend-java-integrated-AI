package com.devano.blog_app.category;

import com.devano.blog_app.entity.Category;
import com.devano.blog_app.repository.jpa.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@Profile("test")
@Transactional
@ComponentScan(basePackages = {
        "com.devano.blog_app.repository",
        "com.devano.blog_app.entity"
})
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void given_category_should_return_saved_category() {
        Category category = Category.builder()
                .name("news")
                .slug("news1")
                .isDeleted(false)
                .build();
        Category savedCategory = categoryRepository.save(category);
        Assertions.assertEquals("news",savedCategory.getName());
        Assertions.assertEquals("news1",savedCategory.getSlug());
        Assertions.assertFalse(savedCategory.getIsDeleted());
    }

    @Test
    public void given_category_id_should_delete_category_with_the_given_id() {
        Category category1 = Category.builder()
                .name("news")
                .slug("news1")
                .isDeleted(false)
                .build();

        Category category2 = Category.builder()
                .name("news")
                .slug("news2")
                .isDeleted(false)
                .build();

        categoryRepository.saveAll(List.of(category1,category2));
        categoryRepository.deleteBySlug("news1");
        Optional<Category> deletedCategory = categoryRepository.findBySlug("news1");
        Assertions.assertTrue(deletedCategory.isEmpty());
    }


}
