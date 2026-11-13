package com.amap.api.maps;

import android.graphics.Point;
import android.view.MotionEvent;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.List;

public final class AMap implements Marker.Host, Circle.Host, Polyline.Host, UiSettings.Listener {
    public static final int MAP_TYPE_NORMAL = 1;
    public static final int MAP_TYPE_SATELLITE = 2;
    public static final int MAP_TYPE_NIGHT = 3;

    public interface OnMapTouchListener {
        void onTouch(MotionEvent event);
    }

    public interface OnCameraChangeListener {
        void onCameraChange(CameraPosition position);
        void onCameraChangeFinish(CameraPosition position);
    }

    public interface OnMapLongClickListener {
        void onMapLongClick(LatLng point);
    }

    public interface OnMarkerClickListener {
        boolean onMarkerClick(Marker marker);
    }

    public interface RendererHost extends Projection.ViewportProvider {
        void requestRedraw();
    }

    private final RendererHost host;
    private final UiSettings uiSettings;
    private final Projection projection;
    private CameraPosition cameraPosition = new CameraPosition(new LatLng(13.7563, 100.5018), 12.5f, 0f, 0f);
    private int mapType = MAP_TYPE_NORMAL;
    private boolean trafficEnabled = true;
    private boolean buildingsEnabled = true;
    private boolean mapTextEnabled = true;
    private CustomMapStyleOptions customMapStyle;

    private final List<Marker> markers = new ArrayList<>();
    private final List<Circle> circles = new ArrayList<>();
    private final List<Polyline> polylines = new ArrayList<>();

    private OnMapTouchListener onMapTouchListener;
    private OnCameraChangeListener onCameraChangeListener;
    private OnMapLongClickListener onMapLongClickListener;
    private OnMarkerClickListener onMarkerClickListener;

    public AMap(RendererHost host) {
        this.host = host;
        this.uiSettings = new UiSettings(this);
        this.projection = new Projection(host);
    }

    public void setMapType(int type) {
        this.mapType = type;
        requestRedraw();
    }

    public int getMapType() { return mapType; }

    public UiSettings getUiSettings() { return uiSettings; }

    public Projection getProjection() { return projection; }

    public CameraPosition getCameraPosition() { return cameraPosition; }

    public void moveCamera(CameraUpdate update) {
        applyCameraUpdate(update);
    }

    public void animateCamera(CameraUpdate update) {
        applyCameraUpdate(update);
    }

    private void applyCameraUpdate(CameraUpdate update) {
        if (update == null) return;
        switch (update.type) {
            case LAT_LNG:
                if (update.target != null) {
                    cameraPosition = new CameraPosition(update.target, cameraPosition.zoom,
                            cameraPosition.tilt, cameraPosition.bearing);
                }
                break;
            case LAT_LNG_ZOOM:
                if (update.target != null) {
                    cameraPosition = new CameraPosition(update.target, clampZoom(update.zoom),
                            cameraPosition.tilt, cameraPosition.bearing);
                }
                break;
            case CAMERA_POSITION:
                if (update.cameraPosition != null) {
                    CameraPosition cp = update.cameraPosition;
                    cameraPosition = new CameraPosition(cp.target, clampZoom(cp.zoom), cp.tilt, cp.bearing);
                }
                break;
            case BOUNDS:
                if (update.bounds != null && update.bounds.southwest != null && update.bounds.northeast != null) {
                    double centerLat = (update.bounds.southwest.latitude + update.bounds.northeast.latitude) * 0.5;
                    double centerLon = (update.bounds.southwest.longitude + update.bounds.northeast.longitude) * 0.5;
                    double latSpan = Math.max(0.001, Math.abs(update.bounds.northeast.latitude - update.bounds.southwest.latitude));
                    double lonSpan = Math.max(0.001, Math.abs(update.bounds.northeast.longitude - update.bounds.southwest.longitude));
                    double maxSpan = Math.max(latSpan, lonSpan);
                    float computedZoom = (float) Math.max(5.0, Math.min(18.0, Math.log(120.0 / maxSpan) / Math.log(2.0)));
                    cameraPosition = new CameraPosition(new LatLng(centerLat, centerLon), computedZoom,
                            cameraPosition.tilt, cameraPosition.bearing);
                }
                break;
            case SCROLL_BY:
                if (host != null) {
                    int cx = Math.max(1, host.getWidthPx()) / 2 + Math.round(update.scrollDx);
                    int cy = Math.max(1, host.getHeightPx()) / 2 + Math.round(update.scrollDy);
                    LatLng next = projection.fromScreenLocation(new Point(cx, cy));
                    cameraPosition = new CameraPosition(next, cameraPosition.zoom,
                            cameraPosition.tilt, cameraPosition.bearing);
                }
                break;
            case ZOOM_BY:
                cameraPosition = new CameraPosition(cameraPosition.target,
                        clampZoom(cameraPosition.zoom + update.zoomDelta),
                        cameraPosition.tilt, cameraPosition.bearing);
                break;
        }
        requestRedraw();
        if (onCameraChangeListener != null) {
            onCameraChangeListener.onCameraChange(cameraPosition);
            onCameraChangeListener.onCameraChangeFinish(cameraPosition);
        }
    }

    private float clampZoom(float z) {
        return Math.max(3f, Math.min(20f, z));
    }

    public Marker addMarker(MarkerOptions options) {
        Marker marker = new Marker(options != null ? options : new MarkerOptions(), this);
        markers.add(marker);
        requestRedraw();
        return marker;
    }

    public Circle addCircle(CircleOptions options) {
        Circle circle = new Circle(options != null ? options : new CircleOptions(), this);
        circles.add(circle);
        requestRedraw();
        return circle;
    }

    public Polyline addPolyline(PolylineOptions options) {
        Polyline polyline = new Polyline(options != null ? options : new PolylineOptions(), this);
        polylines.add(polyline);
        requestRedraw();
        return polyline;
    }

    public void clear() {
        markers.clear();
        circles.clear();
        polylines.clear();
        requestRedraw();
    }

    public void setOnMapTouchListener(OnMapTouchListener listener) {
        this.onMapTouchListener = listener;
    }

    public void setOnCameraChangeListener(OnCameraChangeListener listener) {
        this.onCameraChangeListener = listener;
    }

    public void setOnMapLongClickListener(OnMapLongClickListener listener) {
        this.onMapLongClickListener = listener;
    }

    public void setOnMarkerClickListener(OnMarkerClickListener listener) {
        this.onMarkerClickListener = listener;
    }

    public void setCustomMapStyle(CustomMapStyleOptions options) {
        this.customMapStyle = options;
        requestRedraw();
    }

    public void setTrafficEnabled(boolean enabled) {
        this.trafficEnabled = enabled;
        requestRedraw();
    }

    public void showBuildings(boolean enabled) {
        this.buildingsEnabled = enabled;
        requestRedraw();
    }

    public void showMapText(boolean enabled) {
        this.mapTextEnabled = enabled;
        requestRedraw();
    }

    public boolean isTrafficEnabled() { return trafficEnabled; }
    public boolean isBuildingsEnabled() { return buildingsEnabled; }
    public boolean isMapTextEnabled() { return mapTextEnabled; }
    public CustomMapStyleOptions getCustomMapStyle() { return customMapStyle; }

    public List<Marker> getMarkers() { return markers; }
    public List<Circle> getCircles() { return circles; }
    public List<Polyline> getPolylines() { return polylines; }

    public OnMapTouchListener getOnMapTouchListener() { return onMapTouchListener; }
    public OnCameraChangeListener getOnCameraChangeListener() { return onCameraChangeListener; }
    public OnMapLongClickListener getOnMapLongClickListener() { return onMapLongClickListener; }
    public OnMarkerClickListener getOnMarkerClickListener() { return onMarkerClickListener; }

    @Override
    public void onMarkerChanged() {
        requestRedraw();
    }

    @Override
    public void onMarkerRemoved(Marker marker) {
        markers.remove(marker);
        requestRedraw();
    }

    @Override
    public void onCircleChanged() {
        requestRedraw();
    }

    @Override
    public void onCircleRemoved(Circle circle) {
        circles.remove(circle);
        requestRedraw();
    }

    @Override
    public void onPolylineRemoved(Polyline polyline) {
        polylines.remove(polyline);
        requestRedraw();
    }

    @Override
    public void onUiSettingsChanged() {
        requestRedraw();
    }

    private void requestRedraw() {
        if (host != null) host.requestRedraw();
    }
}
