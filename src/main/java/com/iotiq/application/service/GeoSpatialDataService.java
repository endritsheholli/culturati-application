package com.iotiq.application.service;

import com.iotiq.application.config.TenantContext;
import com.iotiq.application.exception.GeoJsonFileOperationException;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class GeoSpatialDataService {

    @Value("${mapFileDirectory}")
    private Resource fileDirectory;

    public void saveGeoJSONFile(MultipartFile file) {
        try {
            File directory = createGeoJsonFile();
            file.transferTo(directory);

        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("geoJsonSaveError", exp);
        }
    }


    public void deleteGeoJSONFile() {
        try {
            File fileToDelete = getGeoJsonFile();

            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        } catch (Exception exp) {
            throw new GeoJsonFileOperationException("geoJsonDeleteError", exp);
        }
    }

    public Resource getGeoJSONFile() {
        File file = getGeoJsonFile();
        return new FileSystemResource(file);
    }

    private File createGeoJsonFile() {
        File directory = getGeoJsonFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    private File getGeoJsonFile() {
        try {
            String tenantName = TenantContext.getCurrentTenant();
            String fileName = tenantName + ".geojson";
            return new File(fileDirectory.getFile().getAbsolutePath(), fileName);
        } catch (IOException exp) {
            throw new EntityNotFoundException("geoJsonNotFound");
        }
    }
}
