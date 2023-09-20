package com.iotiq.application.controller;

import com.iotiq.application.messages.navpoint.NavEdgeDto;
import com.iotiq.application.service.NavEdgeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Edge", description = "Edges API")
@RequestMapping("/api/v1/edges")
@RestController
@RequiredArgsConstructor
public class NavEdgeController {

    private final NavEdgeService navEdgeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UUID create(@RequestBody NavEdgeDto request) {
        return navEdgeService.create(request);
    }

}
