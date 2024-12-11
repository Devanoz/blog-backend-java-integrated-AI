package com.devano.blog_app.mapper;

import com.devano.blog_app.entity.Category;
import com.devano.blog_app.response.category.CreateCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);

    CreateCategoryResponse mapCategoryToCreateCategoryResponse(Category category);
}
