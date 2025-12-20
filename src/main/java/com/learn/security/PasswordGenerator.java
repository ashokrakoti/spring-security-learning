package com.learn.security;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        System.out.println("user123  -> " + encoder.encode("user123"));
        System.out.println("admin123 -> " + encoder.encode("admin123"));
    }
}

//user123  -> {bcrypt}$2a$10$Bfo7vCeHz7uwprqzjg/L8OZRUt18zq5zr06Z.XQYhjoVoNK4ci9IG
//admin123 -> {bcrypt}$2a$10$YTckMDoECCsBT9ukAusRPukefsD/aMJUw6LOvgG4TlTnU4yI7FNim

//user123  -> {bcrypt}$2a$10$0swlIhmystzN2X.tH0MCI.aKZlA37VFWohl0/M/FkFNicJzTJyCdO
//admin123 -> {bcrypt}$2a$10$GZ9Cr7dz9ShtAiITE4BK6Oh81rTFyooVwwK96SqDyhGDZK/fKvGYi
