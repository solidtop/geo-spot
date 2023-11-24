package com.example.geospot.place;

import org.geolatte.geom.G2D;
import org.geolatte.geom.crs.CoordinateReferenceSystem;
import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Configuration
public class PlaceConfig {
    @Bean
    GeolatteGeomModule geolatteGeomModule() {
        CoordinateReferenceSystem<G2D> crs = WGS84;
        return new GeolatteGeomModule(crs);
    }
}
