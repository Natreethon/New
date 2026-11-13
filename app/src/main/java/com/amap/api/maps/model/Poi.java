package com.amap.api.maps.model;

public final class Poi {
    private final String name;
    private final LatLng coordinate;
    private final String poiId;

    public Poi(String name, LatLng coordinate, String poiId) {
        this.name = name != null ? name : "";
        this.coordinate = coordinate;
        this.poiId = poiId;
    }

    public String getName() { return name; }
    public LatLng getCoordinate() { return coordinate; }
    public String getPoiId() { return poiId; }
}
