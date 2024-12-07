package com.devano.blog_app.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:secret.properties")
@ConfigurationProperties(prefix = "blog")
public class SecretProperties {
    private String username;
    private String password;
    private String jwtIss;
    private String secretKey;
}
