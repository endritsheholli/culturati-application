package com.iotiq.application.controller;

import com.iotiq.application.config.TenantContext;
import com.iotiq.application.service.GeoSpatialDataService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "Geospatial Data", description = "Geospatial Data API")
@RestController
@RequestMapping("/api/v1/geospatial_data")
@RequiredArgsConstructor
public class GeospatialDataController {

    private final GeoSpatialDataService geoSpatialDataService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(@GeoSpatialDataAuth.CREATE)")
    public void uploadGeoJSON(@RequestParam("file") MultipartFile file) {
        geoSpatialDataService.save(file);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority(@GeoSpatialDataAuth.DELETE)")
    public void deleteGeoJSON() {
        geoSpatialDataService.delete();
    }

    @GetMapping
    @PreAuthorize("hasAuthority(@GeoSpatialDataAuth.VIEW)")
    public ResponseEntity<Resource> getGeospatialData() {
        Resource resource = geoSpatialDataService.get();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + TenantContext.getCurrentTenant() + ".json\"")
                .body(resource);
    }
}
