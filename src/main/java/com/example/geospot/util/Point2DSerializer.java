package com.example.geospot.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;

import java.io.IOException;

public class Point2DSerializer extends JsonSerializer<Point<G2D>> {
    @Override
    public void serialize(Point<G2D> g2DPoint, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(g2DPoint.getPosition().getCoordinate(1));
        jsonGenerator.writeNumber(g2DPoint.getPosition().getCoordinate(2));
        jsonGenerator.writeEndArray();
    }
}
