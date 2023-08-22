package com.iotiq.application.directions;

import com.iotiq.application.directions.domain.Coordinates;
import com.iotiq.application.directions.domain.RouteResponse;
import com.iotiq.application.directions.services.DirectionsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
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
    public RouteResponse findRoute(@RequestBody @Size(min = 2, max = 2) List<Coordinates> request) {
        return directionsService.findRoute(request.get(0), request.get(1));
    }
}
