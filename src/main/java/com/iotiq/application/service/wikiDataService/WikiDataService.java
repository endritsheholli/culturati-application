package com.iotiq.application.service.wikiDataService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public abstract class WikiDataService<T> {

    private List<T> entities = new ArrayList<>();

    public List<T> getEntities(List<T> sourceList) {
        if (entities.isEmpty()) {
            entities = sourceList;
        }
        return entities;
    }
}
