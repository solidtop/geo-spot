package com.example.geospot.place;

import com.example.geospot.category.CategoryId;
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
    public Page<PlaceResponse> getAllPublicPlaces(Pageable pageable) {
        return placeService.getAllPublicPlaces(pageable);
    }

    @GetMapping("/{id}")
    public PlaceResponse getPublicPlaceById(@PathVariable long id) {
        return placeService.getPublicPlaceById(id);
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

    @GetMapping("/category/{categoryId}")
    public Page<PlaceResponse> getPublicPlacesByCategoryId(@PathVariable long categoryId, Pageable pageable) {
        return placeService.getPublicPlacesByCategoryId(categoryId, pageable);
    }

    @GetMapping("/nearby")
    public Page<PlaceResponse> getNearbyPublicPlaces(@Validated NearbyRequest nearbyRequest, Pageable pageable) {
        return placeService.getNearbyPublicPlaces(nearbyRequest, pageable);
    }
}
