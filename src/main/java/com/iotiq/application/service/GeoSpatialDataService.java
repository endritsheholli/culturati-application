package com.iotiq.application.service;

import com.iotiq.application.config.TenantContext;
import com.iotiq.application.exception.FileFormatNotSupportedException;
import com.iotiq.application.exception.FileNotFound;
import com.iotiq.application.exception.FileTransferException;
import com.iotiq.application.util.FileUtil;
import com.iotiq.commons.exceptions.RequiredFieldMissingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class GeoSpatialDataService {

    @Value("${mapFileDirectory}")
    private Resource fileDirectory;
    private static final String FILE_EXTENSION = ".geojson";
    private static final List<String> allowedExtensions = Arrays.asList(".json", ".geojson");

    public void save(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            String fileExtension = FileUtil.getFileExtension(filename);
            if (!allowedExtensions.contains(fileExtension)) {
                throw new FileFormatNotSupportedException(fileExtension);
            }
        }

        File directory = createGeoJSONFile();

        try {
            file.transferTo(directory);
        } catch (IOException e) {
            throw new FileTransferException(directory.getAbsolutePath(), e);
        }
    }

    public void delete() {
        File fileToDelete = getGeoJSONFile();

        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    public Resource get() {
        File file = getGeoJSONFile();

        if (!file.exists()) {
            throw new FileNotFound(file.getName());
        }

        return new FileSystemResource(file);
    }

    private File createGeoJSONFile() {
        File directory = getGeoJSONFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    private File getGeoJSONFile() {
        String filename = buildFilename();

        File directoryFolder;
        try {
            directoryFolder = fileDirectory.getFile();
        } catch (IOException exp) {
            throw new FileNotFound(filename);
        }
        String absolutePathOfFolder = directoryFolder.getAbsolutePath();

        return new File(absolutePathOfFolder, filename);
    }

    private static String buildFilename() {
        String tenantName = TenantContext.getCurrentTenant();
        if (tenantName == null || tenantName.isEmpty()) {
            throw new RequiredFieldMissingException("tenantName");
        }
        return tenantName + FILE_EXTENSION;
    }
}
