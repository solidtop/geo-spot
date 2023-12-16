package com.example.geospot.place;

import com.example.geospot.validation.Latitude;
import com.example.geospot.validation.Longitude;
import jakarta.validation.constraints.NotNull;

public record NearbyRequest(
    @Latitude
    double lat,
    @Longitude
    double lng,
    double radius
) {
}
