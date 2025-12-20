package com.learn.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

//Basic auth version with logs and filters
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .addFilterBefore((servletRequest, servletResponse, filterChain) -> {
//                    System.out.println(">>> Before Security Filters");
//                    filterChain.doFilter(servletRequest, servletResponse);
//                    System.out.println("<<< After Security Filters");
//                }, SecurityContextHolderFilter.class)
//                .csrf(csrf -> csrf.disable())
//                .logout(logout -> logout.disable())
//                .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/public/**").permitAll()
//                .anyRequest().authenticated()
//        )
//                .httpBasic();
//
//        return http.build();
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(session -> session.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/public/**").permitAll()
                    .requestMatchers("/api/admin", "/api/admin/**").hasRole("ADMIN")
                    .requestMatchers("/api/hello").hasRole("USER")
                    .anyRequest().authenticated()
            ).httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(SecurityUsersProperties props) {
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$10$Bfo7vCeHz7uwprqzjg/L8OZRUt18zq5zr06Z.XQYhjoVoNK4ci9IG")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$10$YTckMDoECCsBT9ukAusRPukefsD/aMJUw6LOvgG4TlTnU4yI7FNim")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
