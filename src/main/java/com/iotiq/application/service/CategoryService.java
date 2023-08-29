package com.iotiq.application.service;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    List<CategoryResponse> categories = new ArrayList<>();
    private final WikiService wikiService;

    public List<CategoryResponse> getCategories(){
        if (categories.isEmpty()) {
            this.categories = wikiService.getCategories();
        }
        return this.categories;
    }
}
