package com.iotiq.application.seed;

import com.iotiq.application.domain.Role;
import com.iotiq.user.internal.UserService;
import com.iotiq.user.messages.request.UserCreateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {
    private final UserService userService;
    private final String adminUsername;
    private final String adminPassword;

    public DataSeeder(final UserService userService,
                      @Value("${seed.users.admin.username}") String adminUsername,
                      @Value("${seed.users.admin.password}") final String adminPassword) {
        this.userService = userService;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(String... args) {
        if (userService.existByUserName(adminUsername)) {
            return;
        }
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setUsername(adminUsername);
        userCreateDto.setPassword(adminPassword);
        userCreateDto.setRole(Role.ADMIN);
        userCreateDto.setEmail("admin@culturati.app");
        userCreateDto.setFirstname("Initial Admin Firstname");
        userCreateDto.setLastname("Initial Admin Lastname");
        userService.create(userCreateDto);
    }
}

