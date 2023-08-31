package com.iotiq.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties("security")
@Configuration
@Data
public class SecurityProperties {
    private List<String> allowedPublicApis;

    public String[] getPublicApis(){
        return getAllowedPublicApis().toArray(new String[0]);
    }


}
