package com.devano.blog_app.service;

import com.devano.blog_app.properties.SecretProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

    private SecretProperties secretProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.equals(secretProperties.getUsername())) {
            throw new UsernameNotFoundException("user tidak ditemukan");
        }
        UserDetails user = User.withUsername(secretProperties.getUsername())
                .password(secretProperties.getPassword())
                .authorities("USER")
                .build();
        return user;
    }

}
