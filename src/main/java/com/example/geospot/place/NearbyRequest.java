package com.example.geospot.place;

import com.example.geospot.validation.Latitude;
import com.example.geospot.validation.Longitude;
import jakarta.validation.constraints.NotNull;

public record NearbyRequest(
    @Latitude
    @NotNull
    double lat,
    @Longitude
    @NotNull
    double lng,
    @NotNull
    double radius
) {
}
