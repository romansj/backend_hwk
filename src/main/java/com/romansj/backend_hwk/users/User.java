package com.romansj.backend_hwk.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// For now just a 'dummy' class to encapsulate required data for user
// Placed in separate module to demonstrate packaging structure -- by feature
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Configuration
@PropertySource("classpath:user-config.properties")
public class User {
    @Value("${user.email}")
    private String email;

    @Value("en")
    private String language;

    @Value("${user.name}")
    private String name;
}
