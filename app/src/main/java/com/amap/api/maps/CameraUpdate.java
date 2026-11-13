package com.amap.api.maps;

import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;

public final class CameraUpdate {
    public enum Type {
        LAT_LNG,
        LAT_LNG_ZOOM,
        CAMERA_POSITION,
        BOUNDS,
        SCROLL_BY,
        ZOOM_BY
    }

    public final Type type;
    public final LatLng target;
    public final float zoom;
    public final CameraPosition cameraPosition;
    public final LatLngBounds bounds;
    public final int padding;
    public final float scrollDx;
    public final float scrollDy;
    public final float zoomDelta;

    public CameraUpdate(Type type, LatLng target, float zoom, CameraPosition cameraPosition,
                        LatLngBounds bounds, int padding, float scrollDx, float scrollDy, float zoomDelta) {
        this.type = type;
        this.target = target;
        this.zoom = zoom;
        this.cameraPosition = cameraPosition;
        this.bounds = bounds;
        this.padding = padding;
        this.scrollDx = scrollDx;
        this.scrollDy = scrollDy;
        this.zoomDelta = zoomDelta;
    }
}
