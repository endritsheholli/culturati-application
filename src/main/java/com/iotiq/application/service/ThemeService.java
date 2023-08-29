package com.iotiq.application.service;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.ThemeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    List<ThemeResponse> themes = new ArrayList<>();
    private final WikiService wikiService;

    public List<ThemeResponse> getThemes(){
        if (themes.isEmpty()) {
            this.themes = wikiService.getThemes();
        }
        return this.themes;
    }
}
