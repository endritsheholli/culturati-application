package com.iotiq.application.directions.services.impl;

import com.iotiq.application.directions.domain.*;
import com.iotiq.application.directions.services.OSRMRoutingClientImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Point;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectionsServiceImplTest {

    @InjectMocks
    DirectionsServiceImpl directionsService;

    @Mock
    OSRMRoutingClientImpl routingClientImpl;

//    @Test
//    void findRoute() {
//        Maneuver maneuver = new Maneuver(0, 0, new Point(0, 0), "modifier", "type");
//        Step e11 = new Step("", maneuver, "mode", "drivingSide", "name", Collections.emptyList(),
//                0d, 0d, 0d);
//        List<Step> steps = List.of(e11);
//        Leg leg = new Leg(steps, "summary", 0d, 0d, 0d);
//        Route e1 = new Route(List.of(leg), "weightName", 0d, 0d, 0d);
//
//        when(routingClientImpl.getRoute(any())).thenReturn(new RouteResponse("ok", List.of(e1), Collections.emptyList()));
//
//        List<Point> route = directionsService.findRoute(new Point(8.6737060546875, 50.0677178281599), new Point(9.931640625, 49.79544988802771));
//
//        assertThat(route).isNotNull();
//        assertThat(route).isEqualTo(List.of(new Point(0, 0)));
//    }

    @Test
    void externalApi() {
        OSRMRoutingClientImpl osrmRoutingClient = new OSRMRoutingClientImpl();

        Point start = new Point(47.3026030919394, 7.903547286987306);
        Point end = new Point(47.315464621127305, 7.9082679748535165);
        List<Point> coordinates = List.of(start, end);
        RouteRequest request = new RouteRequest("directions", "driving-car", coordinates, "json");

        RouteResponse route = osrmRoutingClient.getRoute(request);
        System.out.println(route);
    }
}