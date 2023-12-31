package com.iotiq.application.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotiq.application.config.ContainersEnvironment;
import com.iotiq.application.domain.NavEdge;
import com.iotiq.application.domain.NavPoint;
import com.iotiq.application.messages.navpoint.NavEdgeDto;
import com.iotiq.application.messages.navpoint.NavPointCreateRequest;
import com.iotiq.application.repository.NavEdgeRepository;
import com.iotiq.application.repository.NavPointRepository;
import com.iotiq.commons.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
class NavPointTests extends ContainersEnvironment {

    private static UUID navPointId;
    private static UUID startingNavPoint;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    NavPointRepository navPointRepository;
    @Autowired
    NavEdgeRepository navEdgeRepository;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Order(1)
    void testCreateNavPoint_Unauthorized() throws Exception {
        // Test unauthorized creation of NavPoint
        //arrange
        NavPointCreateRequest request = new NavPointCreateRequest(
                null, // location
                Collections.emptyList(), // facilityIds
                Collections.emptyList(), // exhibitionItemIds
                Collections.emptyList() // exhibitIds
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
    void testCreateNavPoint_InvalidData() throws Exception {
        // Test creation of NavPoint with invalid data
        //arrange
        NavPointCreateRequest request = new NavPointCreateRequest(
                null, // location
                List.of(UUID.randomUUID()), // facilityIds
                List.of(UUID.randomUUID()), // exhibitionItemIds
                List.of(UUID.randomUUID()) // exhibitIds
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
    void testCreateNavPoint_Success() throws Exception {
        // Test successful creation of NavPoint
        //arrange
        int databaseSizeBeforeCreate = navPointRepository.findAll().size();
        NavPointCreateRequest request = new NavPointCreateRequest(
                null, // location
                Collections.emptyList(), // facilityIds
                Collections.emptyList(), // exhibitionItemIds
                Collections.emptyList() // exhibitIds
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
    void testCreateNavPointWithEdge() throws Exception {
        // Test creation of NavPoint with an edge
        // Arrange
        List<NavPoint> allNavPoints = navPointRepository.findAll();
        int databaseSizeBeforeCreate = allNavPoints.size();
        startingNavPoint = allNavPoints.get(0).getId();

        NavPointCreateRequest request = new NavPointCreateRequest(
                null, // location
                Collections.emptyList(), // facilityIds
                Collections.emptyList(), // exhibitionItemIds
                Collections.emptyList() // exhibitIds
        );

        // Act
        ResultActions result = mockMvc.perform(
                post("/api/v1/nav_point")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        // Assert and get the id.
        String createdId = result.andExpect(status().isCreated())
                .andReturn()
                .getResponse().getContentAsString().replaceAll("\"", "");

        // Act
        ResultActions resultCreateEdge = mockMvc.perform(
                post("/api/v1/edges")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new NavEdgeDto(startingNavPoint,  UUID.fromString(createdId), Boolean.FALSE)
                        ))
        );

        resultCreateEdge.andExpect(status().isCreated())
                .andReturn();

        assertPersistedNavPoint(navPoints -> {
            assertThat(navPoints).hasSize(databaseSizeBeforeCreate + 1);
            navPointId = navPoints.get(navPoints.size() - 1).getId();

            assert navPointId != null;
            navPointRepository.findById(navPointId)
                    .orElseThrow(() -> new EntityNotFoundException("navPoint"));

            // Assert that the set of edge IDs contains only the specified edgeId
            List<NavEdge> edges = navEdgeRepository.findAll();
            assertThat(edges).hasSize(1);

            assert startingNavPoint != null;
            navPointRepository.findById(startingNavPoint)
                    .orElseThrow(() -> new EntityNotFoundException("navPoint"));

            // Assert that the set of edge IDs in the testEdge contains only the navPointId
            assertThat(edges.stream().map(navEdge -> navEdge.getStartingPoint().getId()))
                    .containsOnly(startingNavPoint);
            assertThat(edges.stream().map(navEdge -> navEdge.getEndingPoint().getId()))
                    .containsOnly(UUID.fromString(createdId));
        });
    }

    @Test
    @Order(5)
    @WithMockUser(authorities = "MUSEUM_MANAGEMENT_CREATE")
    @Transactional
    @Rollback(value = false)
    void testCreateExistingEdgeShouldReturnError() throws Exception {

        List<NavEdge> allEdges = navEdgeRepository.findAll();
        assertThat(allEdges).hasSizeGreaterThanOrEqualTo(1);
        NavEdge existingEdge = allEdges.get(0);
        NavPoint firstPoint = existingEdge.getStartingPoint();
        NavPoint secondPoint = existingEdge.getEndingPoint();
        Boolean directed = existingEdge.getDirected();

        mockMvc.perform(
                post("/api/v1/edges")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new NavEdgeDto(firstPoint.getId(), secondPoint.getId(), Boolean.FALSE)
                        ))
        ).andExpect(status().isConflict())
                .andReturn();

        mockMvc.perform(
                        post("/api/v1/edges")
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new NavEdgeDto(secondPoint.getId(), firstPoint.getId(), Boolean.FALSE)
                                ))
                ).andExpect(status().isConflict())
                .andReturn();

        assertThat(directed).isFalse();
        mockMvc.perform(
                        post("/api/v1/edges")
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new NavEdgeDto(firstPoint.getId(), secondPoint.getId(), Boolean.TRUE)
                                ))
                ).andExpect(status().isConflict())
                .andReturn();

        mockMvc.perform(
                        post("/api/v1/edges")
                                .with(csrf().asHeader())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        new NavEdgeDto(secondPoint.getId(), firstPoint.getId(), Boolean.TRUE)
                                ))
                ).andExpect(status().isConflict())
                .andReturn();


    }

        private void assertPersistedNavPoint(Consumer<List<NavPoint>> navPointAssertion) {
        navPointAssertion.accept(navPointRepository.findAll());
    }

}
