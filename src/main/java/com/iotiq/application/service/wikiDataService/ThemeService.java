package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiService;
import com.iotiq.application.wiki.messages.ThemeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService extends WikiDataService<ThemeResponse> {
    private final WikiService wikiService;

    public List<ThemeResponse> getThemes() {
        return getEntities(wikiService.getThemes());
    }
}
