package com.iotiq.application.controller;

import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavPointCreateRequest;
import com.iotiq.application.messages.navpoint.NavPointDto;
import com.iotiq.application.messages.navpoint.NavPointUpdateRequest;
import com.iotiq.application.service.NavPointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Nav Point", description = "Nav Point API")
@RestController
@RequestMapping("/api/v1/nav_point")
@RequiredArgsConstructor
public class NavPointController {
    private final NavPointService navPointService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.CREATE)")
    public UUID create(@RequestBody @Valid NavPointCreateRequest navPointCreateRequest) {
        return navPointService.create(navPointCreateRequest);
    }

    @GetMapping
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public List<NavPointDto> getAll() {
        List<NavPoint> navPoints = navPointService.getAll();
        return navPoints.stream().map(NavPointDto::of).toList();
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public NavPointDto getOne(@PathVariable UUID id) {
        NavPoint navPoint = navPointService.getOne(id);
        return NavPointDto.of(navPoint);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.DELETE)")
    public void delete(@PathVariable UUID id) {
        navPointService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.UPDATE)")
    public void update(@PathVariable UUID id, @RequestBody @Valid NavPointUpdateRequest request) {
        navPointService.update(id, request);
    }
}
