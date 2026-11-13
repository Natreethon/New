package com.amap.api.services.geocoder;

import com.amap.api.services.core.LatLonPoint;

public final class RegeocodeQuery {
    private final LatLonPoint point;
    private final float radius;
    private final String latLonType;

    public RegeocodeQuery(LatLonPoint point, float radius, String latLonType) {
        this.point = point;
        this.radius = radius;
        this.latLonType = latLonType;
    }

    public LatLonPoint getPoint() { return point; }
    public float getRadius() { return radius; }
    public String getLatLonType() { return latLonType; }
}
