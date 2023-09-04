package com.iotiq.application.controller.wikiDataController;

import com.iotiq.application.service.wikiDataService.ThemeService;
import com.iotiq.application.wiki.messages.ThemeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theme")
@RequiredArgsConstructor
public class ThemeController {
    private final ThemeService themeService;
    @GetMapping
    @PreAuthorize("hasAuthority(@Game.VIEW)")
    public ResponseEntity<List<ThemeResponse>> getAll(){
        return new ResponseEntity<>(themeService.getThemes(), HttpStatus.OK);
    }
}
