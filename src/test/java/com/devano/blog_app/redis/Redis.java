package com.devano.blog_app.redis;

import com.devano.blog_app.entity.redis.Person;
import com.devano.blog_app.repository.redis.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Optional;

@SpringBootTest
@EnableCaching
public class Redis {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testUser() {
        Person person1 = new Person();
        person1.setId("devano");
        person1.setFirstname("devano");
        person1.setLastname("zaidan");
        personRepository.save(person1);
        Person person2 = new Person();
        person2.setId("andi");
        person2.setFirstname("andi");
        person2.setLastname("fuck");
        personRepository.save(person1);
        personRepository.save(person2);
        Iterable<Person> personDevano = personRepository.findAll();
        Optional<Person> devano = personRepository.findById("devano");
        Optional<Person> devano1 = personRepository.findByFirstname("devano");
        Assertions.assertNotNull(personDevano);
        Assertions.assertNotNull(devano);
        Assertions.assertTrue(devano1.isPresent());
    }
}
