package com.iotiq.application.controller;

import com.iotiq.application.messages.Exhibit.ExhibitRequest;
import com.iotiq.application.service.ExhibitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


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
    
}
