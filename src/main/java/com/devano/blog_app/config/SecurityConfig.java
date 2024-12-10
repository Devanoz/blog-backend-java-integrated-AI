package com.devano.blog_app.config;

import com.devano.blog_app.service.UserDetailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailService userDetailService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
         http.csrf(AbstractHttpConfigurer::disable);
         http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         });
         http.authorizeHttpRequests(auth ->{
            auth.requestMatchers("/api/auth/login").permitAll();
            auth.requestMatchers("/actuator/**").permitAll();
            auth.anyRequest().permitAll();
         });
         http.exceptionHandling(handler ->{
             handler.authenticationEntryPoint(authenticationEntryPoint());
             handler.accessDeniedHandler(accessDeniedHandler());
         });
         http.rememberMe(AbstractHttpConfigurer::disable);
         http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
         return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"error\": \""+ exception.getMessage() +"\"}");
            }
        };
    }

    @Bean
    @Primary
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"error\": \""+ exception.getMessage() +"\"}");
            }
        };
    }

    @Bean
    @Primary
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            try (PrintWriter writer = response.getWriter()) {
                writer.write("{\"error\": \""+ exception.getMessage() +"\"}");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}