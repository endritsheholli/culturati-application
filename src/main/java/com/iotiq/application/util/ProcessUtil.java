package com.iotiq.application.util;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class ProcessUtil {

    private final ApplicationContext applicationContext;

    public void killIf(@NotNull Supplier<Boolean> condition) {
        if (condition.get()) {
            System.out.println("Killing application");
            kill();
        }
    }

    public void killIf(@NotNull Supplier<Boolean> condition, String message) {
        if (condition.get()) {
            System.out.println(message);
            kill();
        }
    }

    private void kill() {
        int exit = SpringApplication.exit(applicationContext, () -> 10);
        System.exit(exit);
    }
}
