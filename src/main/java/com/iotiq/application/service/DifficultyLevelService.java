package com.iotiq.application.service;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.DifficultyLevelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultyLevelService {
    List<DifficultyLevelResponse> difficultyLevels = new ArrayList<>();
    private final WikiService wikiService;

    public List<DifficultyLevelResponse> getDifficultyLevels(){
        if (difficultyLevels.isEmpty()) {
            this.difficultyLevels = wikiService.getDifficultyLevels();
        }
        return this.difficultyLevels;
    }
}
