package com.iotiq.application.util;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessUtil {

    private final Logger log = LoggerFactory.getLogger(ProcessUtil.class);

    private final ApplicationContext applicationContext;

    public void killIf(boolean condition) {
        if (condition) {
            log.error("Killing application");
            kill();
        }
    }

    public void killIf(boolean condition, String message) {
        if (condition) {
            log.error(message);
            kill();
        }
    }

    public void kill(String message) {
        log.error(message);
        kill();
    }

    private void kill() {
        int exit = SpringApplication.exit(applicationContext, () -> 10);
        System.exit(exit);
    }
}
