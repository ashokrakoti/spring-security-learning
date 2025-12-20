package com.learn.security.config;

import org.apache.catalina.startup.UserConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.security")
public class SecurityUsersProperties {
    private List<UserConfig> users;

    public List<UserConfig> getUsers() {
        return users;
    }

    public void setUsers(List<UserConfig> users) {
        this.users = users;
    }

    public static class UserConfig {
        private String username;
        private String password;
        private String roles;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }
    }
}
