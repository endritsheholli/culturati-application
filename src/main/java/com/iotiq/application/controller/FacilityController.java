package com.iotiq.application.controller;

import com.iotiq.application.domain.Facility;
import com.iotiq.application.messages.facility.FacilityDto;
import com.iotiq.application.messages.facility.FacilityCreateRequest;
import com.iotiq.application.messages.facility.FacilityUpdateRequest;
import com.iotiq.application.service.FacilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Facility", description = "Facility API")
@RestController
@RequestMapping("/api/v1/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public ResponseEntity<List<FacilityDto>> getAll() {
        List<Facility> facilities = facilityService.getAll();
        return new ResponseEntity<>(facilities.stream().map(FacilityDto::of).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public ResponseEntity<FacilityDto> getOne(@PathVariable UUID id) {
        Facility facility = facilityService.getOne(id);
        if (facility != null) {
            return new ResponseEntity<>(FacilityDto.of(facility), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.CREATE)")
    public ResponseEntity<Void> create(@RequestBody @Valid FacilityCreateRequest facility) {
        facilityService.create(facility);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.UPDATE)")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid FacilityUpdateRequest facility) {
        facilityService.update(id, facility);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.DELETE)")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        facilityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
