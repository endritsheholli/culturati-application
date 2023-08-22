package com.iotiq.application.controller;

import com.iotiq.application.messages.register.RegistrationRequest;
import com.iotiq.application.service.RegistrationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Register", description = "Register API")
@RestController
@RequestMapping("/api/v1/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid RegistrationRequest request){
        registrationService.save(request);
    }
}
