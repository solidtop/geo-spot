package com.example.geospot.place;

import com.example.geospot.validation.Latitude;
import com.example.geospot.validation.Longitude;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record NearbyRequest(
    @Longitude
    @NotNull
    double lng,
    @Latitude
    @NotNull
    double lat,
    @Range(min = 0, max = 1000000)
    @NotNull
    double radius
) {
}
