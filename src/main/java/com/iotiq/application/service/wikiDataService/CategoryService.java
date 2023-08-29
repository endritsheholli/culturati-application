package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService extends WikiDataService<CategoryResponse> {

    private final WikiService wikiService;

    public List<CategoryResponse> getCategories() {
        return getEntities(wikiService.getCategories());
    }
}
