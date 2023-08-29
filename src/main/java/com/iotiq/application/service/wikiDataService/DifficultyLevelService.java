package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultyLevelService extends WikiDataService<DifficultyLevelResponse> {
    private final WikiService wikiService;

    public List<DifficultyLevelResponse> getDifficultyLevels() {
        return getEntities(wikiService.getDifficultyLevels());
    }
}
