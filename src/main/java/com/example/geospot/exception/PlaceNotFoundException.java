package com.example.geospot.exception;

public class PlaceNotFoundException extends ResourceNotFoundException {
    public PlaceNotFoundException() {
        super("Place not found");
    }

    public PlaceNotFoundException(String message) {
        super(message);
    }
}

