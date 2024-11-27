package com.devano.blog_app.entity;

import lombok.Data;

@Data
public class Post {
    private Integer id;
    private String title;
    private String body;
    private String slug;
    private boolean isPublished;
    private boolean isDeleted;
    private Integer createdAt;
    private Integer publishedAt;
}
