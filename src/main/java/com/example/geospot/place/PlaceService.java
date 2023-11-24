package com.example.geospot.place;

import com.example.geospot.category.Category;
import com.example.geospot.category.CategoryRepository;
import com.example.geospot.exception.ApiRequestException;
import com.example.geospot.exception.CategoryNotFoundException;
import com.example.geospot.exception.PlaceNotFoundException;
import com.example.geospot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
    public void updateCategoryId(long id, long categoryId) {
        Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        Category category = new Category();
        category.setId(categoryId);
        place.setCategory(category);
        placeRepository.save(place);
    }

    public void deletePlaceById(long id) {
        placeRepository.deleteById(id);
    }

    public List<PlaceResponse> getPlacesByCategoryName(String categoryName) {
        return placeRepository.findAllByCategoryName(categoryName).stream().map(PlaceResponse::of).toList();
    }

    public List<PlaceResponse> getNearbyPlaces(@Validated NearbyRequest nearbyDto) {
        return placeRepository.findAll().stream().map(PlaceResponse::of).toList();
    }
}
