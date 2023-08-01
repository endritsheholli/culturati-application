package com.iotiq.application.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GeoJSONService {

    private static final String FILE_DIRECTORY = System.getProperty("user.dir") + "/application/src/main/resources/files/";

    public String saveGeoJSONFile(String tenantName, MultipartFile file) throws IOException {
        String fileName = tenantName + ".geojson";

        File directory = new File(FILE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File destFile = new File(directory, fileName);
        file.transferTo(destFile);

        return "The file has been uploaded successfully.";
    }

    public String deleteGeoJSONFile(String tenantName) throws IOException {
        String fileName = tenantName + ".geojson";

        Path filePath = Paths.get(FILE_DIRECTORY, fileName);

        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        return "The file was successfully deleted.";
    }

    public Resource getGeoJSONFile(String tenantName) throws FileNotFoundException {
        String fileName = tenantName + ".geojson";
        Path filePath = Paths.get(FILE_DIRECTORY, fileName);

        Resource resource = new FileSystemResource(filePath.toFile());
        if (!resource.exists()) {
            throw new FileNotFoundException("File not found.");
        }

        return resource;
    }
}
