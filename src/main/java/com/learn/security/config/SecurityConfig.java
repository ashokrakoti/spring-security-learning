package com.learn.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        http
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
        List<UserDetails> userDetails = props.getUsers().stream()
                .map(user -> User.withUsername(user.getUsername())
                        .password("{noop}" + user.getPassword())
                        .roles(user.getRoles().split(","))
                        .build()).toList();

        return new InMemoryUserDetailsManager(userDetails);
    }

}
