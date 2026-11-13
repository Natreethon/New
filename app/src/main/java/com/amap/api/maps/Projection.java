package com.amap.api.maps;

import android.graphics.Point;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;

public final class Projection {
    public interface ViewportProvider {
        int getWidthPx();
        int getHeightPx();
        CameraPosition getCamera();
    }

    private final ViewportProvider provider;

    public Projection(ViewportProvider provider) {
        this.provider = provider;
    }

    public Point toScreenLocation(LatLng latLng) {
        if (latLng == null || provider == null) return new Point(0, 0);
        CameraPosition cam = provider.getCamera();
        int w = Math.max(1, provider.getWidthPx());
        int h = Math.max(1, provider.getHeightPx());
        double scale = 256.0 * Math.pow(2.0, cam.zoom);
        double cx = (cam.target.longitude + 180.0) / 360.0 * scale;
        double cy = latToMercatorY(cam.target.latitude) * scale;
        double px = (latLng.longitude + 180.0) / 360.0 * scale;
        double py = latToMercatorY(latLng.latitude) * scale;
        double dx = px - cx;
        double dy = py - cy;
        double rad = Math.toRadians(-cam.bearing);
        double rx = dx * Math.cos(rad) - dy * Math.sin(rad);
        double ry = dx * Math.sin(rad) + dy * Math.cos(rad);
        double tiltFactor = Math.cos(Math.toRadians(Math.min(60.0, Math.max(0.0, cam.tilt)) * 0.45));
        int sx = (int) Math.round(w * 0.5 + rx);
        int sy = (int) Math.round(h * 0.5 + ry * tiltFactor);
        return new Point(sx, sy);
    }

    public LatLng fromScreenLocation(Point point) {
        if (point == null || provider == null) return new LatLng(13.7563, 100.5018);
        CameraPosition cam = provider.getCamera();
        int w = Math.max(1, provider.getWidthPx());
        int h = Math.max(1, provider.getHeightPx());
        double scale = 256.0 * Math.pow(2.0, cam.zoom);
        double cx = (cam.target.longitude + 180.0) / 360.0 * scale;
        double cy = latToMercatorY(cam.target.latitude) * scale;
        double tiltFactor = Math.max(0.4, Math.cos(Math.toRadians(Math.min(60.0, Math.max(0.0, cam.tilt)) * 0.45)));
        double rx = point.x - w * 0.5;
        double ry = (point.y - h * 0.5) / tiltFactor;
        double rad = Math.toRadians(cam.bearing);
        double dx = rx * Math.cos(rad) - ry * Math.sin(rad);
        double dy = rx * Math.sin(rad) + ry * Math.cos(rad);
        double lon = ((cx + dx) / scale) * 360.0 - 180.0;
        double lat = mercatorYToLat((cy + dy) / scale);
        return new LatLng(Math.max(-85.0, Math.min(85.0, lat)), Math.max(-180.0, Math.min(180.0, lon)));
    }

    public static double latToMercatorY(double lat) {
        double clamped = Math.max(-85.05112878, Math.min(85.05112878, lat));
        double sin = Math.sin(Math.toRadians(clamped));
        return 0.5 - Math.log((1.0 + sin) / (1.0 - sin)) / (4.0 * Math.PI);
    }

    public static double mercatorYToLat(double y) {
        double n = Math.PI - 2.0 * Math.PI * y;
        return Math.toDegrees(Math.atan(0.5 * (Math.exp(n) - Math.exp(-n))));
    }
}
