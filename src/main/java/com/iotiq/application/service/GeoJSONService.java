package com.iotiq.application.service;

import com.iotiq.application.exception.GeoJsonFileNotFoundException;
import com.iotiq.application.exception.GeoJsonFileOperationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class GeoJSONService {

    @Value("${file_directory}")
    private Resource fileDirectory;
    
    public String saveGeoJSONFile(String tenantName, MultipartFile file) throws GeoJsonFileOperationException {
        try {
            String fileName = tenantName + ".geojson";
            File directory = new File(fileDirectory.getFile().getAbsolutePath());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File destFile = new File(directory, fileName);
            file.transferTo(destFile);

            return destFile.getAbsolutePath();
        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("geoJsonSaveError", "geoJsonSaveError", exp);
        }
    }


    public void deleteGeoJSONFile(String tenantName) {
        try {
            String fileName = tenantName + ".geojson";
            File fileToDelete = new File(fileDirectory.getFile().getAbsolutePath(), fileName);

            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("geoJsonDeleteError", "geoJsonDeleteError", exp);
        }
    }

    public Resource getGeoJSONFile(String tenantName) {
        try {
            String fileName = tenantName + ".geojson";
            File file = new File(fileDirectory.getFile().getAbsolutePath(), fileName);

            Resource resource = new FileSystemResource(file);
            if (!resource.exists()) {
                throw new GeoJsonFileNotFoundException("geoJsonNotFound");
            }

            return resource;
        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("geoJsonNotFound", "geoJsonNotFound", exp);
        }
    }
}
