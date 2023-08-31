package com.iotiq.application.controller;

import com.iotiq.application.domain.ExhibitionItem;
import com.iotiq.application.messages.exhibitionItem.ExhibitionItemCreateRequest;
import com.iotiq.application.messages.exhibitionItem.ExhibitionItemDto;
import com.iotiq.application.messages.exhibitionItem.ExhibitionItemFilter;
import com.iotiq.application.messages.exhibitionItem.ExhibitionItemUpdateRequest;
import com.iotiq.application.service.ExhibitionItemService;
import com.iotiq.commons.message.response.PagedResponse;
import com.iotiq.commons.message.response.PagedResponseBuilder;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Exhibition Items", description = "Exhibition Items API")
@RestController
@RequestMapping("/api/v1/exhibition_items")
@RequiredArgsConstructor
public class ExhibitionItemController {

    private final ExhibitionItemService exhibitionItemService;

    @GetMapping
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public PagedResponse<ExhibitionItemDto> getAll(ExhibitionItemFilter filter, Sort sort) {
        Page<ExhibitionItem> page = exhibitionItemService.getAll(filter, sort);
        List<ExhibitionItemDto> dtos = page.getContent().stream().map(ExhibitionItemDto::of).toList();

        return PagedResponseBuilder.createResponse(page, dtos);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.VIEW)")
    public ExhibitionItemDto getOne(@PathVariable UUID id) {
        ExhibitionItem exhibitionItem = exhibitionItemService.getOne(id);
        return ExhibitionItemDto.of(exhibitionItem);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.CREATE)")
    public void create(@RequestBody @Valid ExhibitionItemCreateRequest request) throws Exception {
        exhibitionItemService.create(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.UPDATE)")
    public void update(@PathVariable UUID id, @RequestBody @Valid ExhibitionItemUpdateRequest request) {
        exhibitionItemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(@MuseumManagementAuth.DELETE)")
    public void delete(@PathVariable UUID id) {
        exhibitionItemService.delete(id);
    }
}
