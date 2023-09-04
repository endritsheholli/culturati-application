package com.iotiq.application.controller.wikiDataController;

import com.iotiq.application.service.wikiDataService.CategoryService;
import com.iotiq.application.wiki.messages.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    @PreAuthorize("hasAuthority(@Game.VIEW)")
    public ResponseEntity<List<CategoryResponse>> getAll(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }
}
