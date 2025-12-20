# spring-security-learning
learning project for spring security

#building app with basic auth
Perfect 👍
Let’s **start building the app** and complete **Phase 0 + Phase 1.1 (Basic Auth – minimal setup)**.

I’ll keep this **very structured** so you can *code → run → understand*.

---

# STEP 0 – Create the Base Spring Boot App

## 0.1 Project Details (Locked)

```
Project     : security-learning-app
Java        : 17
Spring Boot : 3.2.x
Build Tool  : Maven
Type        : REST API
```

---

## 0.2 Maven `pom.xml`

Create a new Spring Boot project and use this `pom.xml`:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.learn</groupId>
    <artifactId>security-learning-app</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>security-learning-app</name>
    <description>Spring Security Learning Project</description>

    <properties>
        <java.version>17</java.version>
        <spring-boot.version>3.2.0</spring-boot.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${spring-boot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>

        <!-- Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- Optional (later use) -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok (optional) -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

---

## 0.3 Main Application Class

```java
package com.learn.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityLearningApplication.class, args);
    }
}
```

---

# STEP 1 – Create Simple REST Endpoints

We need **something to protect**.

## 1.1 Controller

```java
package com.learn.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello Public";
    }

    @GetMapping("/api/hello")
    public String securedHello() {
        return "Hello Secured";
    }

    @GetMapping("/api/admin")
    public String adminHello() {
        return "Hello Admin";
    }
}
```

---

# STEP 2 – Observe Default Spring Security Behavior

👉 **DO NOT write any security config yet**

## 2.1 Run the Application

```
mvn spring-boot:run
```

### What you’ll see in logs:

```
Using generated security password: xxxxxxxx
```

---

## 2.2 Test Endpoints

### Public endpoint

```
GET http://localhost:8080/public/hello
```

❌ **Still asks for authentication**

### Why?

Because **Spring Security secures everything by default**.

---

# STEP 3 – Minimal Security Configuration (Phase 1.1)

Now we explicitly define **what is public and what is secured**.

---

## 3.1 Security Configuration Class

```java
package com.learn.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(); // enable Basic Auth

        return http.build();
    }
}
```

---

## 3.2 What We Just Did (Important)

| Line                  | Meaning                     |
| --------------------- | --------------------------- |
| `SecurityFilterChain` | Core security configuration |
| `permitAll()`         | Public endpoint             |
| `authenticated()`     | Requires login              |
| `httpBasic()`         | Enables Basic Auth          |
| No sessions config    | Default behavior            |

---

# STEP 4 – Test Basic Auth

### Call secured API without auth

```
GET /api/hello
```

❌ `401 Unauthorized`

---

### Call with Basic Auth

```
Authorization: Basic base64(user:password)
```

Use credentials shown in startup logs.

✅ Response:

```
Hello Secured
```

---

# 🔥 What You Learned So Far (Critical)

✔ Spring Security auto-config
✔ Default deny-all behavior
✔ SecurityFilterChain is the heart
✔ Basic Auth is filter-based
✔ AuthN vs AuthZ separation

---

# NEXT STEP (Phase 1.2)

👉 **Replace default user with custom in-memory users**

* Define USER and ADMIN
* Test role-based access
* Introduce `UserDetailsService`

### Your Action Before Next Step

1. Run the app
2. Hit all three endpoints
3. Observe behavior

Reply with **“Done”** and we’ll move to **custom users & roles** 🚀






