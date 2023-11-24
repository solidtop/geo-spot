package com.example.geospot.place;

import org.springframework.web.bind.annotation.RequestParam;

public record NearbyRequest(
    @RequestParam
    double lat,
    @RequestParam
    double lng,
    @RequestParam
    double radius
) {
}
