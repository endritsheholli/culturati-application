package com.iotiq.application.wiki;

import com.iotiq.application.wiki.messages.CategoryResponse;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WikiService {
    public List<CategoryResponse> getCategories() {
        return List.of(new CategoryResponse("Art"), new CategoryResponse("History"));
    }

    public List<DifficultyLevelResponse> getDifficultyLevels() {
        return List.of(new DifficultyLevelResponse("child"),
                new DifficultyLevelResponse("basic"),
                new DifficultyLevelResponse("intermediate"),
                new DifficultyLevelResponse("expert"));
    }

}
