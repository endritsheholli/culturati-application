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

    @Value("${file_directory}")
    private Resource fileDirectory;
    
    public String saveGeoJSONFile(MultipartFile file) {
        try {
            File directory = createGeoJsonFile();
            file.transferTo(directory);

            return directory.getAbsolutePath();
        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("Problem in save geoJson", exp);
        }
    }


    public void deleteGeoJSONFile() {
        try {
            File fileToDelete = getGeoJsonFile();

            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("Problem in Delete geoJson.", exp);
        }
    }

    public Resource getGeoJSONFile() {
        try {
            File file = getGeoJsonFile();
            Resource resource = new FileSystemResource(file);
            if (!resource.exists()) {
                throw new EntityNotFoundException("geoJsonNotFound");
            }

            return resource;
        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("Problem in fetch geoJson", exp);
        }
    }
    private File createGeoJsonFile() throws IOException {
        File directory = getGeoJsonFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    private File getGeoJsonFile() throws IOException {
        String tenantName = TenantContext.getCurrentTenant();
        String fileName = tenantName + ".geojson";
        return new File(fileDirectory.getFile().getAbsolutePath(), fileName);
    }
}
