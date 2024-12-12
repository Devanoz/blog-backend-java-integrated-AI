package com.devano.blog_app.controller;

import com.devano.blog_app.request.category.CreateCategoryRequest;
import com.devano.blog_app.response.category.CreateCategoryResponse;
import com.devano.blog_app.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CreateCategoryResponse> createCategory(@RequestBody CreateCategoryRequest categoryRequest) {
        CreateCategoryResponse response = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(response);
    }
}
