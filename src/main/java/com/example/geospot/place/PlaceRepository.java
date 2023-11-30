package com.example.geospot.place;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findAllByVisible(boolean visible, Pageable pageable);
    Page<Place> findAllByUserId(String userId, Pageable pageable);
    Page<Place> findAllByVisibleAndCategoryId(boolean visible, long categoryId, Pageable pageable);
    Optional<Place> findByIdAndVisible(long id, boolean visible);
    Optional<Place> findByIdAndUserId(long id, String userId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM place p " +
                    "WHERE visible=true AND " +
            "ST_Distance_Sphere(p.coordinate, ST_GeometryFromText(:pointText, 4326)) <= :radius * 1000")
    Page<Place> findAllNearby(@Param("pointText") String pointText, @Param("radius") double radius, Pageable pageable);
}
