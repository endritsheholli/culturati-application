package com.iotiq.application.wiki;

import com.iotiq.application.wiki.messages.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WikiService {
    public List<CategoryResponse> getCategories() {
        return List.of(new CategoryResponse("Art"), new CategoryResponse("History"));
    }

}
