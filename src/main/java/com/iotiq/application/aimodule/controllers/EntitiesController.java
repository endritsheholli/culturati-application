package com.iotiq.application.aimodule.controllers;

import com.iotiq.application.aimodule.messages.EntitiesResponse;
import com.iotiq.application.aimodule.services.EntitiesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Entities", description = "Entities API.")
@RestController
@RequestMapping("/api/v1/entities")
@RequiredArgsConstructor
public class EntitiesController {

    private final EntitiesService entitiesService;

    @GetMapping
//@PreAuthorize("hasRole('ROLE_AI_MODULE')")
    public EntitiesResponse get() {
        return entitiesService.get();
    }
}
