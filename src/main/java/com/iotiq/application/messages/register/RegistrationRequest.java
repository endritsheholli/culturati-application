package com.iotiq.application.messages.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
        @NotEmpty(message = "registrationRequest.username")
        @Size(min = 1, max = 50, message = "registrationRequest.usernameSize")
        String username,
        String firstname,
        String lastname,
        @Email(message = "registrationRequest.emailFormat")
        @NotEmpty(message = "registrationRequest.email")
        String email,
        @NotEmpty(message = "registrationRequest.password")
        @Size(min = 4, max = 100, message = "registrationRequest.passwordSize")
        String password
) {
}
