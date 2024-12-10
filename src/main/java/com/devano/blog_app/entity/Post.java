package com.devano.blog_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(length = 100, nullable = false,unique = true)
    private String slug;

    @Column(nullable = false)
    private Boolean isPublished = false;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "comment_count",nullable = false)
    private Integer commentCount = 0;

    @Column(name = "updated_at")
    private Long updatedAt;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "published_at")
    private Long publishedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @PrePersist
    public void onCreate() {
        long currentEpochTime = Instant.now().toEpochMilli();
        this.createdAt = currentEpochTime;
        this.updatedAt = currentEpochTime;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now().toEpochMilli();
    }

}