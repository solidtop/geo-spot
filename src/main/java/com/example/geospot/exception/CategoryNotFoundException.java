package com.example.geospot.exception;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException() {
        super("Category not found");
    }

    public CategoryNotFoundException(long categoryId) {
        super("Category not found with id " + categoryId);
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
