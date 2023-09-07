package com.iotiq.application.controller;

import com.iotiq.application.service.wikiDataService.ArtWorkService;
import com.iotiq.application.wiki.messages.ArtWorkResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "artworks", description = "artworks API")
@RestController
@RequestMapping("/api/v1/artworks")
@RequiredArgsConstructor
public class ArtWorkController {
    private final ArtWorkService artWorkService;
    @GetMapping
    @PreAuthorize("hasAuthority(@GameAuth.VIEW)")
    public ResponseEntity<List<ArtWorkResponse>> getAll(){
        return new ResponseEntity<>(artWorkService.getArtWorks(), HttpStatus.OK);
    }
}
