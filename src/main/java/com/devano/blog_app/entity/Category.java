package com.devano.blog_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String slug;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted;

    @Column
    private Long updatedAt;

    @Column(nullable = false)
    private Long createdAt;

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
