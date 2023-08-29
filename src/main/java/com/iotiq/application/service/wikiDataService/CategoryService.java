package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements WikiDataService<CategoryResponse> {
    private final WikiService wikiService;
    private List<CategoryResponse> categories = new ArrayList<>();

    @Override
    public List<CategoryResponse> getData() {
        if (categories.isEmpty()) {
            this.categories = wikiService.getCategories();
        }
        return this.categories;
    }
}
