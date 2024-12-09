package com.devano.blog_app.repository.redis;

import com.devano.blog_app.entity.redis.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, String> {

    Optional<Person> findByFirstname(String firstname);
}
