package com.amap.api.maps.model;

public final class LatLngBounds {
    public final LatLng southwest;
    public final LatLng northeast;

    public LatLngBounds(LatLng southwest, LatLng northeast) {
        this.southwest = southwest;
        this.northeast = northeast;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private double minLat = Double.POSITIVE_INFINITY;
        private double maxLat = Double.NEGATIVE_INFINITY;
        private double minLon = Double.POSITIVE_INFINITY;
        private double maxLon = Double.NEGATIVE_INFINITY;
        private boolean hasPoints = false;

        public Builder() {}

        public Builder include(LatLng point) {
            if (point == null) return this;
            hasPoints = true;
            minLat = Math.min(minLat, point.latitude);
            maxLat = Math.max(maxLat, point.latitude);
            minLon = Math.min(minLon, point.longitude);
            maxLon = Math.max(maxLon, point.longitude);
            return this;
        }

        public LatLngBounds build() {
            if (!hasPoints) {
                LatLng def = new LatLng(13.7563, 100.5018);
                return new LatLngBounds(def, def);
            }
            return new LatLngBounds(new LatLng(minLat, minLon), new LatLng(maxLat, maxLon));
        }
    }
}
