package com.example.geospot.user;

import com.example.geospot.place.PlaceResponse;
import com.example.geospot.place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final PlaceService placeService;

    @Autowired
    public UserController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("/places")
    public Page<PlaceResponse> getAllUserPlaces(Pageable pageable) {
        return placeService.getAllUserPlaces(pageable);
    }
}
