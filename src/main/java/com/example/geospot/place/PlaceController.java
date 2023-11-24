package com.example.geospot.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/places")
public class PlaceController {
    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewPlace(@RequestBody @Validated PlaceRequest placeRequest) {
        placeService.addNewPlace(placeRequest);
    }

    @GetMapping
    public Page<PlaceResponse> getAllPlaces(Pageable pageable) {
        return placeService.getAllPlaces(pageable);
    }

    @GetMapping("/{id}")
    public PlaceResponse getPlaceById(@PathVariable long id) {
        return placeService.getPlaceById(id);
    }

    @PutMapping("/{id}")
    public void updatePlace(@PathVariable long id, @RequestBody @Validated PlaceRequest placeRequest) {
        placeService.updatePlace(id, placeRequest);
    }

    @PatchMapping("/{id}") // TODO: Not completed yet
    public void updateCategoryId(@PathVariable long id, @RequestBody long categoryId) {
        placeService.updateCategoryId(id, categoryId);
    }

    @DeleteMapping("/{id}")
    public void deletePlaceById(@PathVariable long id) {
        placeService.deletePlaceById(id);
    }

    @GetMapping("/category/{categoryName}")
    public List<PlaceResponse> getPlacesByCategoryName(@PathVariable String categoryName) {
        return placeService.getPlacesByCategoryName(categoryName);
    }

    @GetMapping("/nearby")
    public List<PlaceResponse> getNearbyPlaces(@Validated NearbyRequest nearbyRequest) {
        return placeService.getNearbyPlaces(nearbyRequest);
    }
}
