package com.iotiq.application.directions;

import com.iotiq.application.directions.messages.FindRouteRequest;
import com.iotiq.application.directions.services.DirectionsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Directions", description = "Directions API")
@RestController
@RequestMapping("/api/v1/directions")
@RequiredArgsConstructor
public class DirectionsController {
    private final DirectionsService directionsService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAuthority(@ExhibitionAuth.CREATE)")
    public List<Point> findRoute(@RequestBody @Valid FindRouteRequest request) {
        return directionsService.findRoute(request.from(), request.to());
    }

}
