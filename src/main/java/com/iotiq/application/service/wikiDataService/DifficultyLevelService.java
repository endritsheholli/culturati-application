package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultyLevelService implements WikiDataService<DifficultyLevelResponse> {
    private final WikiService wikiService;
    private List<DifficultyLevelResponse> difficultyLevels = new ArrayList<>();

    @Override
    public List<DifficultyLevelResponse> getData() {
        if (difficultyLevels.isEmpty()) {
            this.difficultyLevels = wikiService.getDifficultyLevels();
        }
        return this.difficultyLevels;
    }
}
