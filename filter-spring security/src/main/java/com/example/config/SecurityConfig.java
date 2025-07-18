package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/public").permitAll()
                        .requestMatchers("/hello").hasRole("USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {});
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        var user = User.withUsername("user")
                .password("{noop}password")
                .roles("USER")
                .build();
        var admin = User.withUsername("admin")
                .password("{noop}adminpass")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
