package com.iotiq.application.controller;

import com.iotiq.application.domain.FacilityOrEstablishment;
import com.iotiq.application.messages.facility.FacilityDto;
import com.iotiq.application.messages.facility.FacilityRequest;
import com.iotiq.application.service.FacilityOrEstablishmentService;
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
public class FacilityOrEstablishmentController {
    private final FacilityOrEstablishmentService facilityService;

    @GetMapping
    @PreAuthorize("hasAuthority(@FacilityAuth.VIEW)")
    public ResponseEntity<List<FacilityDto>> getAll() {
        List<FacilityOrEstablishment> facilities = facilityService.getAll();
        return new ResponseEntity<>(facilities.stream().map(FacilityDto::of).toList(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(@FacilityAuth.VIEW)")
    public ResponseEntity<FacilityDto> getOne(@PathVariable UUID id) {
        FacilityOrEstablishment facility = facilityService.getOne(id);
        if (facility != null) {
            return new ResponseEntity<>(FacilityDto.of(facility), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasAuthority(@FacilityAuth.CREATE)")
    public ResponseEntity<Void> create(@RequestBody @Valid FacilityRequest facility) {
        facilityService.create(facility);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(@FacilityAuth.UPDATE)")
    public ResponseEntity<Void> update(@PathVariable UUID id,
                                    @RequestBody @Valid FacilityRequest facility) {
        facilityService.update(id, facility);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(@FacilityAuth.DELETE)")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        facilityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
