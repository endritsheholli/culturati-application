package com.iotiq.application.service;

import com.iotiq.application.exception.GeoJsonFileNotFoundException;
import com.iotiq.application.exception.GeoJsonFileOperationException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GeoJSONService {

    private static final String FILE_DIRECTORY = System.getProperty("user.dir") + "/application/src/main/resources/files/";

    public String saveGeoJSONFile(String tenantName, MultipartFile file) throws GeoJsonFileOperationException {
        String fileName = tenantName + ".geojson";

        try {
            File directory = new File(FILE_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File destFile = new File(directory, fileName);
            file.transferTo(destFile);

            return destFile.getAbsolutePath();
        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("file_save_error", "Error while saving the GeoJSON file.", exp);
        }
    }


    public void deleteGeoJSONFile(String tenantName) throws GeoJsonFileOperationException {
        String fileName = tenantName + ".geojson";

        Path filePath = Paths.get(FILE_DIRECTORY, fileName);
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException exp) {
            throw new GeoJsonFileOperationException("file_delete_error", "Error while deleting the GeoJSON file.", exp);
        }
    }

    public Resource getGeoJSONFile(String tenantName) throws GeoJsonFileNotFoundException {
        String fileName = tenantName + ".geojson";
        Path filePath = Paths.get(FILE_DIRECTORY, fileName);

        Resource resource = new FileSystemResource(filePath.toFile());
        if (!resource.exists()) {
            throw new GeoJsonFileNotFoundException("File not found.");
        }

        return resource;
    }
}
