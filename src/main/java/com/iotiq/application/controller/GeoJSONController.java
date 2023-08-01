package com.iotiq.application.controller;

import com.iotiq.application.service.GeoJSONService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Tag(name = "Geospatial", description = "Geospatial API")
@RestController
@RequestMapping("/api/v1/geojson")
@RequiredArgsConstructor
public class GeoJSONController {

    private final GeoJSONService geoJSONService;
    

    @PostMapping("/{tenantName}")
    @PreAuthorize("hasAuthority(@GeoJSONAuth.CREATE)")
    public ResponseEntity<String> uploadGeoJSON(@PathVariable String tenantName, @RequestParam("file") MultipartFile file) {
        try {
            String message = geoJSONService.saveGeoJSONFile(tenantName, file);

            return ResponseEntity.ok(message);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{tenantName}")
    @PreAuthorize("hasAuthority(@GeoJSONAuth.DELETE)")
    public ResponseEntity<String> deleteGeoJSON(@PathVariable String tenantName) {
        try {
            String message = geoJSONService.deleteGeoJSONFile(tenantName);

            return ResponseEntity.ok(message);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File deletion error: " + e.getMessage());
        }
    }

    @GetMapping("/{tenantName}")
    @PreAuthorize("hasAuthority(@GeoJSONAuth.VIEW)")
    public ResponseEntity<Resource> getGeoJSON(@PathVariable String tenantName) {
        try {
            Resource resource = geoJSONService.getGeoJSONFile(tenantName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tenantName + ".json\"")
                    .body(resource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
