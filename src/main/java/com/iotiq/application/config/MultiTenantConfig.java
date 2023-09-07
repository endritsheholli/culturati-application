package com.iotiq.application.config;

import com.iotiq.application.exception.FileNotFoundException;
import com.iotiq.application.exception.TenantDataSourceConnectionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MultiTenantConfig {

    @Value("${defaultTenant}")
    private String defaultTenant;

    @Value("${tenantPropertiesPath}")
    private String tenantPropertiesPath;

    @Bean
    @ConfigurationProperties(prefix = "tenants")
    public DataSource dataSource() throws IOException {
        File[] files;

        try {
            files = new ClassPathResource(tenantPropertiesPath).getFile().listFiles();
        }
        catch (IOException e) {
            throw new FileNotFoundException(tenantPropertiesPath, e);
        }
        Map<Object, Object> resolvedDataSources = new HashMap<>();

        for (File propertyFile : files) {
            Properties tenantProperties = new Properties();
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

            try (FileInputStream inStream = new FileInputStream(propertyFile)) {
                tenantProperties.load(inStream);
                String tenantId = tenantProperties.getProperty("name");
                dataSourceBuilder.driverClassName(tenantProperties.getProperty("datasource.driver-class-name"));
                dataSourceBuilder.username(tenantProperties.getProperty("datasource.username"));
                dataSourceBuilder.password(tenantProperties.getProperty("datasource.password"));
                dataSourceBuilder.url(tenantProperties.getProperty("datasource.url"));
                resolvedDataSources.put(tenantId, dataSourceBuilder.build());
            } catch (IOException exp) {
                throw new TenantDataSourceConnectionException("Problem in tenant datasource:", exp);
            }
        }

        AbstractRoutingDataSource dataSource = new MultiTenantDataSource();
        dataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSources);

        dataSource.afterPropertiesSet();
        return dataSource;
    }

}