package com.example.geospot.place;

import com.example.geospot.category.Category;
import com.example.geospot.category.CategoryId;
import com.example.geospot.category.CategoryRepository;
import com.example.geospot.exception.CategoryNotFoundException;
import com.example.geospot.exception.PlaceNotFoundException;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Page<PlaceResponse> getAllPublicPlaces(Pageable pageable) {
        return placeRepository.findAllByVisible(true, pageable).map(PlaceResponse::of);
    }

    public Page<PlaceResponse> getAllUserPlaces(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return placeRepository.findAllByUserId(username, pageable).map(PlaceResponse::of);
    }

    public PlaceResponse getPublicPlaceById(long id) {
        return placeRepository.findByIdAndVisible(id, true).map(PlaceResponse::of).orElseThrow(
                PlaceNotFoundException::new);
    }

    @Transactional
    public void addNewPlace(@Validated PlaceRequest placeRequest) {
        long categoryId = placeRequest.categoryId() == 0 ? 1 : placeRequest.categoryId();
        categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Place place = new Place();
        place.setCoordinate(Place.toCoordinate(placeRequest.longitude(), placeRequest.latitude()));
        place.setName(placeRequest.name());
        place.setDescription(placeRequest.description());
        place.setVisible(placeRequest.visible());

        Category category = new Category();
        category.setId(categoryId);
        place.setCategory(category);
        place.setUserId(username);

        placeRepository.save(place);
    }

    @Transactional
    public void updatePlace(long id, @Validated PlaceRequest placeRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Place place = placeRepository.findByIdAndUserId(id, username).orElseThrow(PlaceNotFoundException::new);
        categoryRepository.findById(placeRequest.categoryId()).orElseThrow(CategoryNotFoundException::new);

        Point<G2D> coordinate = Place.toCoordinate(placeRequest.longitude(), placeRequest.latitude());
        place.setCoordinate(coordinate);
        place.setName(placeRequest.name());
        place.setDescription(placeRequest.description());
        place.setVisible(placeRequest.visible());
        Category category = new Category();
        category.setId(placeRequest.categoryId());
        place.setCategory(category);

        placeRepository.save(place);
    }

    @Transactional
    public void updateCategoryId(long id, @Validated CategoryId categoryId) {
        Place place = placeRepository.findById(id).orElseThrow(PlaceNotFoundException::new);
        categoryRepository.findById(categoryId.id()).orElseThrow(CategoryNotFoundException::new);
        Category category = new Category();
        category.setId(categoryId.id());
        place.setCategory(category);

        placeRepository.save(place);
    }

    @Transactional
    public void deletePlaceById(long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        placeRepository.findByIdAndUserId(id, username).orElseThrow(PlaceNotFoundException::new);
        placeRepository.deleteById(id);
    }

    public Page<PlaceResponse> getPublicPlacesByCategoryId(long categoryId, Pageable pageable) {
        return placeRepository.findAllByVisibleAndCategoryId(true, categoryId, pageable).map(PlaceResponse::of);
    }

    public Page<PlaceResponse> getNearbyPublicPlaces(@Validated NearbyRequest nearbyRequest, Pageable pageable) {
        String pointText = "POINT(" + nearbyRequest.lat() + " " + nearbyRequest.lng() + ")";
        double radius = nearbyRequest.radius();
        return placeRepository.findAllNearby(pointText, radius, pageable).map(PlaceResponse::of);
    }
}
