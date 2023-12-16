package com.example.geospot.place;

import com.example.geospot.category.Category;
import com.example.geospot.util.Point2DSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Entity
@Getter
@Setter
@ToString
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonSerialize(using = Point2DSerializer.class)
    private Point<G2D> coordinate;
    @NotBlank
    private String name;
    @NotNull
    private String description;
    @ManyToOne
    private Category category;
    private boolean visible;
    @Column(nullable = false)
    private String userId;
    @CreationTimestamp
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public static Point<G2D> toCoordinate(double lng, double lat) {
        return new Point<>(new G2D(lng, lat), WGS84);
    }
}
