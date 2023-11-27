package com.example.geospot.exception;

public class PlaceNotFoundException extends ResourceNotFoundException {
    public PlaceNotFoundException() {
        super("Place not found");
    }

    public PlaceNotFoundException(long placeId) {
        super("Place not found with id " + placeId);
    }

    public PlaceNotFoundException(String message) {
        super(message);
    }
}

