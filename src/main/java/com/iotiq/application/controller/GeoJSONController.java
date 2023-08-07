package com.iotiq.application.controller;

import com.iotiq.application.config.TenantContext;
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


@Tag(name = "Geospatial", description = "Geospatial API")
@RestController
@RequestMapping("/api/v1/geojson")
@RequiredArgsConstructor
public class GeoJSONController {

    private final GeoJSONService geoJSONService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority(@GeoJSONAuth.CREATE)")
    public ResponseEntity<Void> uploadGeoJSON(@RequestParam("file") MultipartFile file) throws Exception {
        String tenantName = TenantContext.getCurrentTenant();
        geoJSONService.saveGeoJSONFile(tenantName, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAuthority(@GeoJSONAuth.DELETE)")
    public ResponseEntity<Void> deleteGeoJSON() {
        String tenantName = TenantContext.getCurrentTenant();
        geoJSONService.deleteGeoJSONFile(tenantName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority(@GeoJSONAuth.VIEW)")
    public ResponseEntity<Resource> getGeoJSON() {
        String tenantName = TenantContext.getCurrentTenant();
        Resource resource = geoJSONService.getGeoJSONFile(tenantName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + tenantName + ".json\"")
                .body(resource);
    }
}
