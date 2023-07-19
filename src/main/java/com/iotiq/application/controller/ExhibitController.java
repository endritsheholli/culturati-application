package com.iotiq.application.controller;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.messages.exhibit.ExhibitDto;
import com.iotiq.application.messages.exhibit.ExhibitFilter;
import com.iotiq.application.messages.exhibit.ExhibitRequest;
import com.iotiq.application.service.ExhibitService;
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

@Tag(name = "Exhibit", description = "Exhibit API")
@RestController
@RequestMapping("/api/v1/exhibit")
@RequiredArgsConstructor
public class ExhibitController {
    private final ExhibitService exhibitService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority(@ExhibitionAuth.CREATE)")
    public void create(@RequestBody @Valid ExhibitRequest request) throws Exception {
        exhibitService.create(request);
    }
    
    @GetMapping
    @PreAuthorize("hasAuthority(@ExhibitionAuth.VIEW)")
    public PagedResponse<ExhibitDto> getAll(ExhibitFilter filter, Sort sort) throws Exception {
        Page<Exhibit> page = exhibitService.getAll(filter, sort);
        List<ExhibitDto> dtos = page.getContent().stream().map(ExhibitDto::of).toList();
        return PagedResponseBuilder.createResponse(page, dtos);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(@ExhibitionAuth.VIEW)")
    public ExhibitDto getOne(@PathVariable UUID id) throws Exception{
        Exhibit exhibit = exhibitService.getOne(id);
        return ExhibitDto.of(exhibit);
    }
    
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(@ExhibitionAuth.UPDATE)")
    public void update(@PathVariable UUID id, @RequestBody @Valid ExhibitRequest request) throws Exception{
        exhibitService.update(id, request);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(@ExhibitionAuth.DELETE)")
    public void delete(@PathVariable UUID id) throws Exception{
        exhibitService.delete(id);
    }
    
}
