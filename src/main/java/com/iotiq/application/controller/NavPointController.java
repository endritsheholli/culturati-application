package com.iotiq.application.controller;

import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavPointCreateRequest;
import com.iotiq.application.messages.navpoint.NavPointDto;
import com.iotiq.application.service.NavPointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PreAuthorize("hasAuthority(@NavPointAuth.CREATE)")
    public ResponseEntity<Void> create(@RequestBody @Valid NavPointCreateRequest navPointCreateRequest) {
        navPointService.create(navPointCreateRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAuthority(@NavPointAuth.VIEW)")
    public ResponseEntity<List<NavPointDto>> getAll(){
        List<NavPoint> navPoints = navPointService.getAll();
        return new ResponseEntity<>(navPoints.stream().map(NavPointDto::of).toList(), HttpStatus.OK);
    }
}
