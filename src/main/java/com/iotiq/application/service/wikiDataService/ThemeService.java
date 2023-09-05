package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiDummyDataHolder;
import com.iotiq.application.wiki.messages.ThemeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {
    public List<ThemeResponse> getThemes() {
        return WikiDummyDataHolder.themes;
    }
}
