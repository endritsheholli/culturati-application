package com.iotiq.application.directions.services;

import com.iotiq.application.directions.domain.RouteRequest;
import com.iotiq.application.directions.domain.RouteResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OSRMRoutingClientImpl implements RoutingClient<RouteResponse, RouteRequest> {


    String scheme = "http";
    String host = "router.project-osrm.org";


    private final WebClient webClient = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public RouteResponse getRoute(RouteRequest request) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(scheme)
                        .host(host)
                        .pathSegment(request.service())
                        .pathSegment("v1")
                        .pathSegment(request.profile())
                        .pathSegment(request.coordinates().toString())
                        .build())
                .retrieve()
//                .onStatus(status -> HttpStatus.BAD_REQUEST == status, response -> Mono.error(new AssetShellException()))
//                .onStatus(status -> HttpStatus.UNAUTHORIZED == status, response -> Mono.error(new AuthenticationException()))
                .bodyToMono(RouteResponse.class)
                .block();
    }
}
