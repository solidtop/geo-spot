package com.example.geospot.place;

import com.example.geospot.category.Category;
import com.example.geospot.util.Point2DSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Entity
@Table
public class Place implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonSerialize(using = Point2DSerializer.class)
    private Point<G2D> coordinate;
    @NotBlank
    @Size(max = 255)
    private String name;
    @Size(max = 255)
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    private String status;
    private long userId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Point<G2D> getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Point<G2D> coordinate) {
        this.coordinate = coordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static Place of(PlaceRequest placeRequest) {
        Place place = new Place();

        Point<G2D> coordinate = new Point<>(new G2D(placeRequest.longitude(), placeRequest.latitude()), WGS84);
        place.setCoordinate(coordinate);

        place.setName(placeRequest.name());
        place.setDescription(placeRequest.description());

        Category category = new Category();
        category.setId(placeRequest.categoryId());
        category.addPlace(place);
        place.setCategory(category);

        return place;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Place place = (Place) o;
        return getId() != null && Objects.equals(getId(), place.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", coordinate=" + coordinate +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
