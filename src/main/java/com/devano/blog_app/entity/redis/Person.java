package com.devano.blog_app.entity.redis;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("person")
@Data
public class Person {
    @Id
    private String id;
    @Indexed
    private String firstname;
    private String lastname;
}
