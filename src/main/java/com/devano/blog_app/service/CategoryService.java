package com.devano.blog_app.service;

import com.devano.blog_app.entity.Category;
import com.devano.blog_app.mapper.CategoryMapper;
import com.devano.blog_app.repository.jpa.CategoryRepository;
import com.devano.blog_app.request.category.CreateCategoryRequest;
import com.devano.blog_app.response.category.CreateCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CreateCategoryResponse createCategory(CreateCategoryRequest categoryRequest) {
        Category savedCategory = categoryRepository.save(
                Category.builder()
                        .name(categoryRequest.getName())
                        .slug(categoryRequest.getSlug())
                        .build()
        );
        return CategoryMapper.mapper.mapCategoryToCreateCategoryResponse(savedCategory);
    }
}
