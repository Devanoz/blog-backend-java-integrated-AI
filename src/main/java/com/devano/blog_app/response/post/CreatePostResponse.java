package com.devano.blog_app.response.post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {
    private Long id;
    private String title;
    private String slug;
    private String body;
    private Long publishedAt;
    private Integer commentCount;
}
