package com.iotiq.application.config;

import com.iotiq.application.util.TenantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class MultiTenantConfig {

    @Value("${defaultTenant}")
    private String defaultTenant;

    private final TenantUtil tenantUtil;

    @Bean
    @ConfigurationProperties(prefix = "tenants")
    public DataSource dataSource() {
        File[] files = tenantUtil.getFiles();

        Map<Object, Object> resolvedDataSources = new HashMap<>();

        for (File propertyFile : files) {
            Properties tenantProperties = new Properties();
            TenantUtil.loadPropertiesFromFile(propertyFile, tenantProperties);
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
            String tenantId = tenantProperties.getProperty("name");
            dataSourceBuilder.driverClassName(tenantProperties.getProperty("datasource.driver-class-name"));
            dataSourceBuilder.username(tenantProperties.getProperty("datasource.username"));
            dataSourceBuilder.password(tenantProperties.getProperty("datasource.password"));
            dataSourceBuilder.url(tenantProperties.getProperty("datasource.url"));
            resolvedDataSources.put(tenantId, dataSourceBuilder.build());
        }

        AbstractRoutingDataSource dataSource = new MultiTenantDataSource();
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);

        dataSource.afterPropertiesSet();
        return dataSource;
    }

}