package com.devano.blog_app.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentResponse {
    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;
    private long createdAt;
}
