package com.romansj.backend_hwk.notifications.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:email-config.properties")
@ConfigurationProperties(prefix = "mail.server")
public class EmailConfig {
    @Value("${mail.server.email}")
    private String email;
    @Value("${mail.server.host}")
    private String host;
    @Value("${mail.server.port}")
    private int port;
    @Value("${mail.server.password}")
    private String password;

}