package com.example.geospot.place;

import com.example.geospot.validation.Latitude;
import com.example.geospot.validation.Longitude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

public record PlaceRequest(
        @Latitude
        double latitude,
        @Longitude
        double longitude,
        @NotBlank
        @Size(max = 255)
        String name,
        @Size(max = 255)
        String description,
        long categoryId
) implements Serializable {
        public static PlaceRequest of(Place place) {
                return new PlaceRequest(
                        place.getCoordinate().getPosition().getLat(),
                        place.getCoordinate().getPosition().getLon(),
                        place.getName(),
                        place.getDescription(),
                        place.getCategory().getId()
                );
        }
}