package com.iotiq.application.controller;

import com.iotiq.application.messages.register.VisitorRegistrationRequest;
import com.iotiq.user.internal.UserService;
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
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Valid VisitorRegistrationRequest request) {
        userService.create(request);
    }
}
