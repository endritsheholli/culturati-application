package com.iotiq.application.service.wikiDataService;

import com.iotiq.application.wiki.WikiDummyDataHolder;
import com.iotiq.application.wiki.messages.ArtWorkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtWorkService {


    public List<ArtWorkResponse> getArtWorks() {
        return WikiDummyDataHolder.artWorks;
    }
}
