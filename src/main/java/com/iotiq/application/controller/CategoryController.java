package com.iotiq.application.controller;

import com.iotiq.application.service.wikiDataService.CategoryService;
import com.iotiq.application.service.wikiDataService.DifficultyLevelService;
import com.iotiq.application.wiki.messages.CategoryResponse;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "categories", description = "categories API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final DifficultyLevelService difficultyLevelService;
    
    @GetMapping
    @PreAuthorize("hasAuthority(@GameAuth.VIEW)")
    public ResponseEntity<List<CategoryResponse>> getAll(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/{categoryName}/difficulty-levels")
    @PreAuthorize("hasAuthority(@GameAuth.VIEW)")
    public ResponseEntity<List<DifficultyLevelResponse>> getAll(@PathVariable String categoryName){
        return new ResponseEntity<>(difficultyLevelService.getDifficultyLevels(categoryName), HttpStatus.OK);
    }
}
