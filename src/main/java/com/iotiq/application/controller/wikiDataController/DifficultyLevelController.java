package com.iotiq.application.controller.wikiDataController;

import com.iotiq.application.service.wikiDataService.DifficultyLevelService;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/difficulty_level")
@RequiredArgsConstructor
public class DifficultyLevelController {
    private final DifficultyLevelService difficultyLevelService;
    
    @GetMapping("/{categoryName}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public ResponseEntity<List<DifficultyLevelResponse>> getAll(@PathVariable String categoryName){
        return new ResponseEntity<>(difficultyLevelService.getDifficultyLevels(categoryName), HttpStatus.OK);
    } 
}
