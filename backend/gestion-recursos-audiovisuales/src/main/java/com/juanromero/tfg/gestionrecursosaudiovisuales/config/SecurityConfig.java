package com.juanromero.tfg.gestionrecursosaudiovisuales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/auth/**").permitAll()
    				.requestMatchers(HttpMethod.OPTIONS).permitAll().requestMatchers("/**").permitAll()
    				.requestMatchers("/resources/**", "/webjars/**", "/WEB-INF/**", "/static/**").permitAll()
    				.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").anonymous()
    				.requestMatchers(HttpMethod.POST, "/book/**", "/videogame/**", "/movie/**", "/album/**").authenticated()
    				.requestMatchers(HttpMethod.PUT, "/book/**", "/videogame/**", "/movie/**", "/album/**").authenticated()
    				.requestMatchers(HttpMethod.DELETE, "/book/**", "/videogame/**", "/movie/**", "/album/**").authenticated()
    				.requestMatchers(HttpMethod.GET, "/book/**", "/videogame/**", "/movie/**", "/album/**").anonymous()
    				.requestMatchers("/uservideogame/**", "/usermovie/**", "/userbook/**", "/useralbum/**").authenticated()
                    .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
