package com.iotiq.application.service;

import com.iotiq.application.domain.Role;
import com.iotiq.application.messages.register.RegistrationRequest;
import com.iotiq.commons.util.PasswordUtil;
import com.iotiq.user.domain.User;
import com.iotiq.user.exceptions.DuplicateUserDataException;
import com.iotiq.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.iotiq.commons.util.NullHandlerUtil.setIfNotNull;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    public void save(RegistrationRequest request) {
        // Check if the username or email is already in use
        if (userRepository.findByAccountInfoUsername(request.username()).isPresent()) {
            throw new DuplicateUserDataException("username");
        }
        if (userRepository.findByPersonalInfoEmail(request.email()).isPresent()) {
            throw new DuplicateUserDataException("email");
        }
        
        // Create a new user with VISITOR role
        User user = new User();
        setIfNotNull(user::setPassword, () -> passwordUtil.encode(request.password()), request.password());
        user.setRole(Role.VISITOR); // Set the role to VISITOR
        setIfNotNull(user::setUsername, request::username);
        setIfNotNull(s -> user.getPersonalInfo().setFirstName(s), request::firstname);
        setIfNotNull(s -> user.getPersonalInfo().setLastName(s), request::lastname);
        setIfNotNull(s -> user.getPersonalInfo().setEmail(s), request::email);

        userRepository.save(user);
    }
}
