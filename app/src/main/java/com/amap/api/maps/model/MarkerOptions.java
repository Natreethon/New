package com.amap.api.maps.model;

public final class MarkerOptions {
    private LatLng position = new LatLng(13.7563, 100.5018);
    private String title = "";
    private float anchorU = 0.5f;
    private float anchorV = 1.0f;
    private boolean flat = false;
    private BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker();
    private float zIndex = 0f;

    public MarkerOptions position(LatLng position) {
        if (position != null) this.position = position;
        return this;
    }

    public MarkerOptions title(String title) {
        this.title = title != null ? title : "";
        return this;
    }

    public MarkerOptions anchor(float u, float v) {
        this.anchorU = u;
        this.anchorV = v;
        return this;
    }

    public MarkerOptions setFlat(boolean flat) {
        this.flat = flat;
        return this;
    }

    public MarkerOptions icon(BitmapDescriptor icon) {
        if (icon != null) this.icon = icon;
        return this;
    }

    public MarkerOptions zIndex(float zIndex) {
        this.zIndex = zIndex;
        return this;
    }

    public LatLng getPosition() { return position; }
    public String getTitle() { return title; }
    public float getAnchorU() { return anchorU; }
    public float getAnchorV() { return anchorV; }
    public boolean isFlat() { return flat; }
    public BitmapDescriptor getIcon() { return icon; }
    public float getZIndex() { return zIndex; }
}
