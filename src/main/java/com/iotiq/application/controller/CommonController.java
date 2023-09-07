package com.iotiq.application.controller;

import com.iotiq.application.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/common")
@RequiredArgsConstructor
public class CommonController {


    @GetMapping("/enums")
    public ResponseEntity<Object> getAllEnums() {

        final Map<String, Object> enums = new HashMap<>();

        enums.put("Roles", Role.values());


        return ResponseEntity
                .ok(enums);
    }
}
