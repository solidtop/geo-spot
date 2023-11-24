package com.example.geospot.place;

import jakarta.validation.constraints.NotBlank;

public record PlaceName(@NotBlank String name) {
}
