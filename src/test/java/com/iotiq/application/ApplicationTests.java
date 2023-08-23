package com.iotiq.application;

import com.iotiq.application.config.ContainersEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
class ApplicationTests extends ContainersEnvironment {

    @Test
    void contextLoads() {
        assertThat(2 + 3).isEqualTo(5);
    }

}
