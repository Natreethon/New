package com.amap.api.maps.model;

import java.util.Objects;

public final class LatLng {
    public final double latitude;
    public final double longitude;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LatLng)) return false;
        LatLng other = (LatLng) o;
        return Double.compare(other.latitude, latitude) == 0
                && Double.compare(other.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "lat/lng: (" + latitude + "," + longitude + ")";
    }
}
