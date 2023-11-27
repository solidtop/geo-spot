package com.example.geospot.place;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findAllByCategoryName(String categoryName, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM place p " +
            "WHERE ST_Distance_Sphere(p.coordinate, ST_GeometryFromText(:pointText, 4326)) <= :radius * 1000")
    Page<Place> findNearbyPlaces(@Param("pointText") String pointText, @Param("radius") double radius, Pageable pageable);
}
