package com.example.geospot;

import org.geolatte.geom.json.GeolatteGeomModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class GeoSpotApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoSpotApplication.class, args);
	}
}
