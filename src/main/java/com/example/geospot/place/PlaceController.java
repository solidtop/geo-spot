package com.example.geospot.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePlace(@PathVariable long id, @RequestBody @Validated PlaceRequest placeRequest) {
        placeService.updatePlace(id, placeRequest);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCategoryId(@PathVariable long id, @RequestBody @Validated CategoryId categoryId) {
        placeService.updateCategoryId(id, categoryId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlaceById(@PathVariable long id) {
        placeService.deletePlaceById(id);
    }

    @GetMapping("/category/{categoryName}")
    public Page<PlaceResponse> getPlacesByCategoryName(@PathVariable String categoryName, Pageable pageable) {
        return placeService.getPlacesByCategoryName(categoryName, pageable);
    }

    @GetMapping("/nearby")
    public Page<PlaceResponse> getNearbyPlaces(@Validated NearbyRequest nearbyRequest, Pageable pageable) {
        return placeService.getNearbyPlaces(nearbyRequest, pageable);
    }
}
