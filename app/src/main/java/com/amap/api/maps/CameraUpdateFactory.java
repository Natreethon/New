package com.amap.api.maps;

import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;

public final class CameraUpdateFactory {
    private CameraUpdateFactory() {}

    public static CameraUpdate newLatLngZoom(LatLng latLng, float zoom) {
        return new CameraUpdate(CameraUpdate.Type.LAT_LNG_ZOOM, latLng, zoom, null, null, 0, 0f, 0f, 0f);
    }

    public static CameraUpdate newLatLng(LatLng latLng) {
        return new CameraUpdate(CameraUpdate.Type.LAT_LNG, latLng, 0f, null, null, 0, 0f, 0f, 0f);
    }

    public static CameraUpdate newCameraPosition(CameraPosition cameraPosition) {
        return new CameraUpdate(CameraUpdate.Type.CAMERA_POSITION, null, 0f, cameraPosition, null, 0, 0f, 0f, 0f);
    }

    public static CameraUpdate newLatLngBounds(LatLngBounds bounds, int padding) {
        return new CameraUpdate(CameraUpdate.Type.BOUNDS, null, 0f, null, bounds, padding, 0f, 0f, 0f);
    }

    public static CameraUpdate scrollBy(float xPixel, float yPixel) {
        return new CameraUpdate(CameraUpdate.Type.SCROLL_BY, null, 0f, null, null, 0, xPixel, yPixel, 0f);
    }

    public static CameraUpdate zoomBy(float amount) {
        return new CameraUpdate(CameraUpdate.Type.ZOOM_BY, null, 0f, null, null, 0, 0f, 0f, amount);
    }
}
