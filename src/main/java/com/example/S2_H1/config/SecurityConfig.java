package com.example.S2_H1.config;

import com.example.S2_H1.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtTokenFilter jwtTokenFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
      .authorizeHttpRequests((requests) -> requests
        .anyRequest().permitAll()
      )
      .csrf(AbstractHttpConfigurer::disable);
    return http.build();
  }
}
