package com.example.geospot.place;

import com.example.geospot.util.Point2DSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import java.time.ZonedDateTime;

public record PlaceResponse(
        long id,
        @JsonSerialize(using = Point2DSerializer.class)
        Point<G2D> coordinate,
        String name,
        String description,
        String category,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt) {
    public static PlaceResponse of(Place place) {
        return new PlaceResponse(
                place.getId(),
                place.getCoordinate(),
                place.getName(),
                place.getDescription(),
                place.getCategory().getName(),
                place.getCreatedAt(),
                place.getUpdatedAt()
        );
    }
}
