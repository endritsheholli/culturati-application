package com.iotiq.application.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotiq.application.config.ContainersEnvironment;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavPointCreateRequest;
import com.iotiq.application.messages.navpoint.NavPointUpdateRequest;
import com.iotiq.application.repository.NavPointRepository;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
public class NavPointTests extends ContainersEnvironment {

    private static UUID navPointId;
    private static UUID edgeId;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    NavPointRepository navPointRepository;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Order(1)
    public void testCreateNavPoint_Unauthorized() throws Exception {
        // Test unauthorized creation of NavPoint
        //arrange
        NavPointCreateRequest request = new NavPointCreateRequest(
                null, // location
                null, // facilityIds
                null, // exhibitionItemIds
                null, // exhibitIds
                null  // edgeIds
        );
        //act
        mockMvc.perform(
                        post("/api/v1/nav_point")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    @WithMockUser(authorities = "MUSEUM_MANAGEMENT_CREATE")
    public void testCreateNavPoint_InvalidData() throws Exception {
        // Test creation of NavPoint with invalid data
        //arrange
        NavPointCreateRequest request = new NavPointCreateRequest(
                null, // location
                List.of(UUID.randomUUID()), // facilityIds
                List.of(UUID.randomUUID()), // exhibitionItemIds
                List.of(UUID.randomUUID()), // exhibitIds
                List.of(UUID.randomUUID())// edgeIds
        );
        //act
        mockMvc.perform(post("/api/v1/nav_point")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(3)
    @WithMockUser(authorities = "MUSEUM_MANAGEMENT_CREATE")
    public void testCreateNavPoint_Success() throws Exception {
        // Test successful creation of NavPoint
        //arrange
        int databaseSizeBeforeCreate = navPointRepository.findAll().size();
        NavPointCreateRequest request = new NavPointCreateRequest(
                null, // location
                null, // facilityIds
                null, // exhibitionItemIds
                null, // exhibitIds
                null  // edgeIds
        );

        //act
        ResultActions result = mockMvc.perform(
                post("/api/v1/nav_point")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //assert
        result.andExpect(status().isCreated())
                .andReturn();

        assertPersistedNavPoint(navPoints -> assertThat(navPoints).hasSize(databaseSizeBeforeCreate + 1));
    }

    @Test
    @Order(4)
    @WithMockUser(authorities = "MUSEUM_MANAGEMENT_CREATE")
    @Transactional
    @Rollback(value = false)
    public void testCreateNavPointWithEdge() throws Exception {
        // Test creation of NavPoint with an edge
        // Arrange
        int databaseSizeBeforeCreate = navPointRepository.findAll().size();
        edgeId = navPointRepository.findAll().get(0).getId();
        NavPointCreateRequest request = new NavPointCreateRequest(
                null, // location
                null, // facilityIds
                null, // exhibitionItemIds
                null, // exhibitIds
                Collections.singletonList(edgeId) // edgeIds
        );

        // Act
        ResultActions result = mockMvc.perform(
                post("/api/v1/nav_point")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        result.andExpect(status().isCreated())
                .andReturn();

        assertPersistedNavPoint(navPoints -> {
            assertThat(navPoints).hasSize(databaseSizeBeforeCreate + 1);
            navPointId = navPoints.get(navPoints.size() - 1).getId();

            assert navPointId != null;
            NavPoint testNavPoint = navPointRepository.findById(navPointId)
                    .orElseThrow(() -> new EntityNotFoundException("navPoint"));

            // Assert that the set of edge IDs contains only the specified edgeId
            assertThat(testNavPoint.getEdges().stream().map(AbstractPersistable::getId))
                    .containsOnly(edgeId);

            assert edgeId != null;
            NavPoint testEdge = navPointRepository.findById(edgeId)
                    .orElseThrow(() -> new EntityNotFoundException("navPoint"));

            // Assert that the set of edge IDs in the testEdge contains only the navPointId
            assertThat(testEdge.getEdges().stream().map(AbstractPersistable::getId))
                    .containsOnly(navPointId);
        });
    }

    @Test
    @Order(5)
    @WithMockUser(authorities = "MUSEUM_MANAGEMENT_UPDATE")
    @Transactional
    @Rollback(value = false)
    public void testUpdateNavPointWithRemoveEdge() throws Exception {
        // Test updating NavPoint by removing an edge
        // Arrange
        NavPointUpdateRequest request = new NavPointUpdateRequest(
                null, // location
                null, // facilityIds
                null, // exhibitionItemIds
                null, // exhibitIds
                null  // edgeIds
        );

        // Act
        ResultActions result = mockMvc.perform(
                put("/api/v1/nav_point/{id}", navPointId)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        result.andExpect(status().isOk())
                .andReturn();

        assertPersistedNavPoint(navPoints -> {

            assert navPointId != null;
            NavPoint testNavPoint = navPointRepository.findById(navPointId)
                    .orElseThrow(() -> new EntityNotFoundException("navPoint"));

            // Assert that the set of edges in the testNavPoint is empty
            assertThat(testNavPoint.getEdges()).isEmpty();

            assert edgeId != null;
            NavPoint testEdge = navPointRepository.findById(edgeId)
                    .orElseThrow(() -> new EntityNotFoundException("navPoint"));

            // Assert that the set of edges in the testEdge is empty
            assertThat(testEdge.getEdges()).isEmpty();
        });
    }

    private void assertPersistedNavPoint(Consumer<List<NavPoint>> navPointAssertion) {
        navPointAssertion.accept(navPointRepository.findAll());
    }

}
