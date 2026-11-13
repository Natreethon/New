package com.amap.api.maps.model;

public final class CircleOptions {
    private LatLng center = new LatLng(13.7563, 100.5018);
    private double radius = 25.0;
    private int fillColor = 0x17087F77;
    private int strokeColor = 0x66087F77;
    private float strokeWidth = 2f;
    private float zIndex = 0f;

    public CircleOptions center(LatLng point) {
        if (point != null) this.center = point;
        return this;
    }

    public CircleOptions radius(double meters) {
        this.radius = meters;
        return this;
    }

    public CircleOptions fillColor(int color) {
        this.fillColor = color;
        return this;
    }

    public CircleOptions strokeColor(int color) {
        this.strokeColor = color;
        return this;
    }

    public CircleOptions strokeWidth(float width) {
        this.strokeWidth = width;
        return this;
    }

    public CircleOptions zIndex(float zIndex) {
        this.zIndex = zIndex;
        return this;
    }

    public LatLng getCenter() { return center; }
    public double getRadius() { return radius; }
    public int getFillColor() { return fillColor; }
    public int getStrokeColor() { return strokeColor; }
    public float getStrokeWidth() { return strokeWidth; }
    public float getZIndex() { return zIndex; }
}
