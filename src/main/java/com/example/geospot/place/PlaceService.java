package com.example.geospot.place;

import com.example.geospot.category.Category;
import com.example.geospot.category.CategoryRepository;
import com.example.geospot.exception.CategoryNotFoundException;
import com.example.geospot.exception.PlaceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, CategoryRepository categoryRepository) {
        this.placeRepository = placeRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addNewPlace(@Validated PlaceRequest placeRequest) {
        boolean categoryExists = categoryRepository.existsById(placeRequest.categoryId());
        if (categoryExists) {
            Place place = Place.of(placeRequest);
            placeRepository.save(place);
        } else {
            throw new CategoryNotFoundException(placeRequest.categoryId());
        }
    }

    public Page<PlaceResponse> getAllPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable).map(PlaceResponse::of);
    }

    public PlaceResponse getPlaceById(long id) {
        return placeRepository.findById(id).map(PlaceResponse::of).orElseThrow(
                PlaceNotFoundException::new);
    }

    @Transactional
    public void updatePlace(long id, @Validated PlaceRequest placeRequest) {
        placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        categoryRepository.findById(placeRequest.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Place place = Place.of(placeRequest);
        placeRepository.save(place);
    }

    @Transactional
    public void updateCategoryId(long id, @Validated CategoryId categoryId) {
        Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        categoryRepository.findById(categoryId.id()).orElseThrow(CategoryNotFoundException::new);
        Category category = new Category();
        category.setId(categoryId.id());
        category.addPlace(place);
        place.setCategory(category);

        placeRepository.save(place);
    }

    @Transactional
    public void deletePlaceById(long id) {
        placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        placeRepository.deleteById(id);
    }

    public Page<PlaceResponse> getPlacesByCategoryName(String categoryName, Pageable pageable) {
        return placeRepository.findAllByCategoryName(categoryName, pageable).map(PlaceResponse::of);
    }

    public Page<PlaceResponse> getNearbyPlaces(@Validated NearbyRequest nearbyRequest, Pageable pageable) {
        String pointText = "POINT(" + nearbyRequest.lng() + " " + nearbyRequest.lat() + ")";
        double radius = nearbyRequest.radius();
        return placeRepository.findNearbyPlaces(pointText, radius, pageable).map(PlaceResponse::of);
    }
}
