package com.amap.api.maps.model;

public final class Marker {
    public interface Host {
        void onMarkerChanged();
        void onMarkerRemoved(Marker marker);
    }

    private final Host host;
    private LatLng position;
    private String title;
    private float anchorU;
    private float anchorV;
    private boolean flat;
    private BitmapDescriptor icon;
    private float zIndex;
    private float rotateAngle = 0f;
    private float alpha = 1f;
    private boolean visible = true;
    private boolean infoWindowShown = false;
    private Object object;

    public Marker(MarkerOptions options, Host host) {
        this.host = host;
        this.position = options.getPosition();
        this.title = options.getTitle();
        this.anchorU = options.getAnchorU();
        this.anchorV = options.getAnchorV();
        this.flat = options.isFlat();
        this.icon = options.getIcon();
        this.zIndex = options.getZIndex();
    }

    public void setPosition(LatLng latLng) {
        if (latLng != null) {
            this.position = latLng;
            notifyHost();
        }
    }

    public LatLng getPosition() { return position; }

    public void setRotateAngle(float rotate) {
        this.rotateAngle = rotate;
        notifyHost();
    }

    public float getRotateAngle() { return rotateAngle; }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        notifyHost();
    }

    public float getAlpha() { return alpha; }

    public void setVisible(boolean visible) {
        this.visible = visible;
        notifyHost();
    }

    public boolean isVisible() { return visible; }

    public void setFlat(boolean flat) {
        this.flat = flat;
        notifyHost();
    }

    public boolean isFlat() { return flat; }

    public void setIcon(BitmapDescriptor icon) {
        if (icon != null) {
            this.icon = icon;
            notifyHost();
        }
    }

    public BitmapDescriptor getIcon() { return icon; }

    public void showInfoWindow() {
        this.infoWindowShown = true;
        notifyHost();
    }

    public boolean isInfoWindowShown() { return infoWindowShown; }

    public void remove() {
        if (host != null) host.onMarkerRemoved(this);
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object getObject() { return object; }

    public String getTitle() { return title; }
    public float getAnchorU() { return anchorU; }
    public float getAnchorV() { return anchorV; }
    public float getZIndex() { return zIndex; }

    private void notifyHost() {
        if (host != null) host.onMarkerChanged();
    }
}
