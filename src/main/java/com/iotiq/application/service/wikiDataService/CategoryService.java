package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiDummyDataHolder;
import com.iotiq.application.wiki.messages.CategoryResponse;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    public List<CategoryResponse> getCategories() {
        return WikiDummyDataHolder.DUMMY_CATEGORIES;
    }

    public List<DifficultyLevelResponse> getDifficultyLevelsByCategoryName(String categoryName) {
        return WikiDummyDataHolder.DUMMY_DIFFICULTY_LEVEL;
    }
}
