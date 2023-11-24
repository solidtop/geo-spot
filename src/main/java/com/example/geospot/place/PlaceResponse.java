package com.example.geospot.place;

import com.example.geospot.util.Point2DSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

public record PlaceResponse(
        @JsonSerialize(using = Point2DSerializer.class)
        Point<G2D> coordinate,
        @NotBlank
        @Size(max = 255)
        String name,
        @Size(max = 255)
        String description,
        @NotBlank
        String category
) {
    public static PlaceResponse of(Place place) {
        return new PlaceResponse(
                place.getCoordinate(),
                place.getName(),
                place.getDescription(),
                place.getCategory().getName()
        );
    }
}
