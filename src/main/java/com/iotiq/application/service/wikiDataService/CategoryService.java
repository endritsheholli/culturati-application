package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiDummyDataHolder;
import com.iotiq.application.wiki.messages.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    public List<CategoryResponse> getCategories() {
        return WikiDummyDataHolder.categories;
    }
}
