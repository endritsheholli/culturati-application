package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.ThemeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService implements WikiDataService<ThemeResponse> {
    private final WikiService wikiService;
    private List<ThemeResponse> themes = new ArrayList<>();

    @Override
    public List<ThemeResponse> getData() {
        if (themes.isEmpty()) {
            this.themes = wikiService.getThemes();
        }
        return this.themes;
    }
}
