package com.devano.blog_app;

import com.devano.blog_app.properties.SecretProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({SecretProperties.class})
public class BlogAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

}
