package com.devano.blog_app.request.post;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    @Size(min = 2, message = "Judul minimal 2 huruf")
    private String title;
    @Size(min = 2)
    private String slug;
    @Size(min = 10)
    private String body;
}
