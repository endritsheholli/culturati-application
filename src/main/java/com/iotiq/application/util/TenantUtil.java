package com.iotiq.application.util;

import com.iotiq.application.domain.Tenant;
import com.iotiq.application.exception.TenantException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@Configuration
public class TenantUtil {

    private final String tenantPropertiesPath;

    private final List<Tenant> tenants;

    public TenantUtil(
            @Value("${tenantPropertiesPath}") String tenantPropertiesPath
    ) {
        this.tenantPropertiesPath = tenantPropertiesPath;
        this.tenants = getTenants();
    }

    public List<Tenant> getTenants() {
        if (tenants != null) {
            return tenants;
        }

        if (StringUtils.isBlank(tenantPropertiesPath))
            throw new TenantException("Exiting because tenant folder path is not configured");

        File[] files = getFiles();
        List<Tenant> foundTenants = new ArrayList<>();

        for (File file : files) {
            Properties tenantProperties = new Properties();
            loadPropertiesFromFile(file, tenantProperties);

            Tenant tenant = Tenant.builder()
                    .name(tenantProperties.getProperty("name"))
                    .datasourceUsername(tenantProperties.getProperty("datasource.username"))
                    .datasourcePassword(tenantProperties.getProperty("datasource.password"))
                    .datasourceUrl(tenantProperties.getProperty("datasource.url"))
                    .datasourceDriverClassName(tenantProperties.getProperty("datasource.driver-class-name"))
                    .build();

            foundTenants.add(tenant);
        }

        return foundTenants;
    }

    public boolean existsByName(String tenantName) {
        return this.tenants.stream().anyMatch(tenant -> tenant.getName().equals(tenantName));
    }

    public static void loadPropertiesFromFile(File file, Properties tenantProperties) {
        try (FileInputStream inStream = new FileInputStream(file)) {
            tenantProperties.load(inStream);
        } catch (IOException e) {
            throw new TenantException("Tenant file is malformed: " + file.getName());
        }
    }

    public File[] getFiles() {
        try {
            File folder = new ClassPathResource(tenantPropertiesPath).getFile();
            return folder.listFiles();
        } catch (IOException e) {
            throw new TenantException(
                    "Exiting because tenant folder is missing. Put the tenant folder and files on the classpath. Classpath is " +
                            tenantPropertiesPath
            );
        }
    }
}
