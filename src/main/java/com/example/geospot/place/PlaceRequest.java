package com.example.geospot.place;

import com.example.geospot.validation.Latitude;
import com.example.geospot.validation.Longitude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlaceRequest(@Latitude double latitude,
                           @Longitude double longitude,
                           @NotBlank String name,
                           @NotNull String description,
                           boolean visible,
                           long categoryId) {
        public PlaceRequest(
                double latitude,
                double longitude,
                String name,
                String description,
                boolean visible,
                long categoryId
        ) {
                this.latitude = latitude;
                this.longitude = longitude;
                this.name = name;
                this.description = description;
                this.visible = visible;
                this.categoryId = categoryId == 0 ? 1 : categoryId;
        }
}
