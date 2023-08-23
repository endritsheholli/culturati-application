package com.iotiq.application.service;

import com.iotiq.application.domain.Role;
import com.iotiq.application.messages.register.RegistrationRequest;
import com.iotiq.user.internal.UserService;
import com.iotiq.user.messages.request.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.iotiq.commons.util.NullHandlerUtil.setIfNotNull;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserService userService;

    public void save(RegistrationRequest request) {
        
        // Create a new UserCreateDto with VISITOR role
        UserCreateDto userCreateDto = new UserCreateDto();
        setIfNotNull(userCreateDto::setPassword, request::password);
        userCreateDto.setRole(Role.VISITOR); // Set the role to VISITOR
        setIfNotNull(userCreateDto::setUsername, request::username);
        setIfNotNull(userCreateDto::setFirstname, request::firstname);
        setIfNotNull(userCreateDto::setLastname, request::lastname);
        setIfNotNull(userCreateDto::setEmail, request::email);

        userService.create(userCreateDto);
    }
}
