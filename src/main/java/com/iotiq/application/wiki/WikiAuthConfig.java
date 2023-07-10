package com.iotiq.application.wiki;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.wiki")
public class WikiAuthConfig {
    private String username;
    private String password;
    private String strategy;
}
