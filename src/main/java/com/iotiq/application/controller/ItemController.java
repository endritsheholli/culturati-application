package com.iotiq.application.controller;

import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.service.ItemService;
import com.iotiq.application.wiki.domain.PageDto;
import com.iotiq.application.wiki.messages.ItemRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Items", description = "Items API")
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Map<Integer, PageDto> getAll() {
        return itemService.getAll();
    }

    @PostMapping
    public ExhibitionItem create(@RequestBody @Valid ItemRequest request) throws Exception {
        return itemService.create(request);
    }
}
