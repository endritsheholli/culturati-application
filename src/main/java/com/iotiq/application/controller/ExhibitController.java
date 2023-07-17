package com.iotiq.application.controller;

import com.iotiq.application.domain.Exhibit;
import com.iotiq.application.messages.Exhibit.ExhibitDto;
import com.iotiq.application.messages.Exhibit.ExhibitRequest;
import com.iotiq.application.service.ExhibitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public List<ExhibitDto> getAll() throws Exception {
        return exhibitService.getAll().stream()
                .map(ExhibitDto::of).toList();
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(@ExhibitionAuth.VIEW)")
    public ExhibitDto getOne(@PathVariable UUID id) throws Exception{
        Exhibit exhibit = exhibitService.getOne(id);
        return ExhibitDto.of(exhibit);
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority(@ExhibitionAuth.UPDATE)")
    public void update(@PathVariable UUID id, @RequestBody @Valid ExhibitRequest request) throws Exception{
        exhibitService.update(id, request);
    }
    
}
