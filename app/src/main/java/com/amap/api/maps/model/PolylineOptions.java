package com.amap.api.maps.model;

import java.util.ArrayList;
import java.util.List;

public final class PolylineOptions {
    private final List<LatLng> points = new ArrayList<>();
    private float width = 8f;
    private int color = 0xFF087F77;
    private float zIndex = 0f;

    public PolylineOptions addAll(Iterable<LatLng> iterable) {
        if (iterable != null) {
            for (LatLng p : iterable) {
                if (p != null) points.add(p);
            }
        }
        return this;
    }

    public PolylineOptions width(float width) {
        this.width = width;
        return this;
    }

    public PolylineOptions color(int color) {
        this.color = color;
        return this;
    }

    public PolylineOptions zIndex(float zIndex) {
        this.zIndex = zIndex;
        return this;
    }

    public List<LatLng> getPoints() { return points; }
    public float getWidth() { return width; }
    public int getColor() { return color; }
    public float getZIndex() { return zIndex; }
}
