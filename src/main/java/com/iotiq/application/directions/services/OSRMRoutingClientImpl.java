package com.iotiq.application.directions.services;

import com.iotiq.application.directions.domain.BBox;
import com.iotiq.application.directions.domain.Metadata;
import com.iotiq.application.directions.domain.RouteRequest;
import com.iotiq.application.directions.domain.RouteResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OSRMRoutingClientImpl implements RoutingClient<RouteResponse, RouteRequest> {

    //    String scheme = "http";
    String scheme = "https";
    //    String host = "router.project-osrm.org";
    String host = "api.openrouteservice.org";
    String token = "5b3ce3597851110001cf624820e4e7f50e2e464aa6fe70f0d5f6db88";

    private final WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public RouteResponse getRoute(RouteRequest request) {
        DirectionsRequest data = new DirectionsRequest(request.coordinates());
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme(scheme)
                        .host(host)
                        .pathSegment("v2")
                        .pathSegment(request.service())
                        .pathSegment(request.profile())
                        .build())
                .header("Authorization", token)
                .bodyValue(data)
                .retrieve()
//                .onStatus(status -> HttpStatus.BAD_REQUEST == status, response -> Mono.error(new AssetShellException()))
//                .onStatus(status -> HttpStatus.UNAUTHORIZED == status, response -> Mono.error(new AuthenticationException()))
                .bodyToMono(RouteResponse.class)
                .block();
    }
}
