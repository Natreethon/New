package com.amap.api.maps.model;

public final class CameraPosition {
    public final LatLng target;
    public final float zoom;
    public final float tilt;
    public final float bearing;

    public CameraPosition(LatLng target, float zoom, float tilt, float bearing) {
        this.target = target != null ? target : new LatLng(13.7563, 100.5018);
        this.zoom = zoom;
        this.tilt = tilt;
        this.bearing = bearing;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition camera) {
        return new Builder(camera);
    }

    public static final class Builder {
        private LatLng target = new LatLng(13.7563, 100.5018);
        private float zoom = 12.5f;
        private float tilt = 0f;
        private float bearing = 0f;

        public Builder() {}

        public Builder(CameraPosition previous) {
            if (previous != null) {
                this.target = previous.target;
                this.zoom = previous.zoom;
                this.tilt = previous.tilt;
                this.bearing = previous.bearing;
            }
        }

        public Builder target(LatLng location) {
            if (location != null) this.target = location;
            return this;
        }

        public Builder zoom(float zoom) {
            this.zoom = zoom;
            return this;
        }

        public Builder tilt(float tilt) {
            this.tilt = tilt;
            return this;
        }

        public Builder bearing(float bearing) {
            this.bearing = bearing;
            return this;
        }

        public CameraPosition build() {
            return new CameraPosition(target, zoom, tilt, bearing);
        }
    }
}
