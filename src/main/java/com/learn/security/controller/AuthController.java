package com.learn.security.controller;

import com.learn.security.dto.LoginRequest;
import com.learn.security.dto.LoginResponse;
import com.learn.security.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
public class AuthController {
     private final AuthenticationManager authenticationManager;
     private final JwtService jwtService;
     private final UserDetailsService userDetailsService;

     public AuthController(AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           UserDetailsService userDetailsService) {
         this.authenticationManager = authenticationManager;
         this.jwtService = jwtService;
         this.userDetailsService = userDetailsService;
     }

     @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
             @RequestBody LoginRequest request
             ) {
         Authentication authentication =
                 authenticationManager.authenticate(
                         new UsernamePasswordAuthenticationToken(
                                 request.getUsername(),
                                 request.getPassword()
                         )
                 );

         UserDetails userDetails =
                 userDetailsService.loadUserByUsername(request.getUsername());

         String token = jwtService.generateToken(userDetails);

         return ResponseEntity.ok(
                 new LoginResponse(
                         token,
                         "Bearer",
                         jwtService.getExpiration()
                 )
         );

     }
}
