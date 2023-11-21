package com.example.geospot.location;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/locations")
public class LocationController {

    @GetMapping
    public String getAllPublicLocations() {
        return "locations";
    }
}
