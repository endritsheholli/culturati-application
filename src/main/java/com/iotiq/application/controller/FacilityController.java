package com.iotiq.application.controller;

import com.iotiq.application.domain.Facility;
import com.iotiq.application.messages.facility.FacilityCreateRequest;
import com.iotiq.application.messages.facility.FacilityDto;
import com.iotiq.application.messages.facility.FacilityUpdateRequest;
import com.iotiq.application.service.FacilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public List<FacilityDto> getAll() {
        List<Facility> facilities = facilityService.getAll();
        return facilities.stream().map(FacilityDto::of).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public FacilityDto getOne(@PathVariable UUID id) {
        Facility facility = facilityService.getOne(id);
        return FacilityDto.of(facility);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.CREATE)")
    public UUID create(@RequestBody @Valid FacilityCreateRequest facility) {
        return facilityService.create(facility);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.UPDATE)")
    public void update(@PathVariable UUID id, @RequestBody @Valid FacilityUpdateRequest facility) {
        facilityService.update(id, facility);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.DELETE)")
    public void delete(@PathVariable UUID id) {
        facilityService.delete(id);
    }
}
