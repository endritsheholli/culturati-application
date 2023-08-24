package com.iotiq.application.util;

import com.iotiq.application.domain.Tenant;
import com.iotiq.commons.exceptions.EntityNotFoundException;
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

    private final ProcessUtil processUtil;

    private final List<Tenant> tenants;

    public TenantUtil(
            @Value("${tenantPropertiesPath}") String tenantPropertiesPath,
            ProcessUtil processUtil
    ) {
        this.tenantPropertiesPath = tenantPropertiesPath;
        this.processUtil = processUtil;
        this.tenants = getTenants();
    }

    public List<Tenant> getTenants() {
        if (tenants != null) {
            return tenants;
        }

        processUtil.killIf(() -> tenantPropertiesPath == null || tenantPropertiesPath.isBlank(),
                "Exiting because tenant folder path is not configured");

        File[] files = getFiles();
        processUtil.killIf(() -> files == null || files.length == 0,
                "Exiting because tenant folder is missing. Put the tenant files in a tenant folder on the classpath. Classpath is " +
                        tenantPropertiesPath
        );

        List<Tenant> tenants = new ArrayList<>();

        for (File file : files) {
            Properties tenantProperties = new Properties();
            try (FileInputStream inStream = new FileInputStream(file)) {
                tenantProperties.load(inStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Tenant tenant = Tenant.builder()
                    .name(tenantProperties.getProperty("datasource.name"))
                    .datasourceUsername(tenantProperties.getProperty("datasource.username"))
                    .datasourcePassword(tenantProperties.getProperty("datasource.password"))
                    .datasourceUrl(tenantProperties.getProperty("datasource.url"))
                    .datasourceDriverClassName(tenantProperties.getProperty("datasource.driver-class-name"))
                    .build();

            tenants.add(tenant);
        }

        return tenants;
    }

    private File[] getFiles() {
        File folder;
        try {
            folder = new ClassPathResource(tenantPropertiesPath).getFile();
        } catch (IOException e) {
            throw new EntityNotFoundException("files");
        }
        return folder.listFiles();
    }
}
