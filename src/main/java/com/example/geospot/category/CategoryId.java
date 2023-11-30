package com.example.geospot.category;

import jakarta.validation.constraints.NotNull;

public record CategoryId(
        @NotNull
        long categoryId
) {
        public long id() {
                return categoryId;
        }
}
