package com.devano.blog_app.response.category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCategoryResponse {
    private String name;
    private String slug;
    private Long createdAt;
}
