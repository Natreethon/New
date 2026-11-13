package com.amap.api.maps.model;

public final class Circle {
    public interface Host {
        void onCircleChanged();
        void onCircleRemoved(Circle circle);
    }

    private final Host host;
    private LatLng center;
    private double radius;
    private final int fillColor;
    private final int strokeColor;
    private final float strokeWidth;
    private boolean visible = true;

    public Circle(CircleOptions options, Host host) {
        this.host = host;
        this.center = options.getCenter();
        this.radius = options.getRadius();
        this.fillColor = options.getFillColor();
        this.strokeColor = options.getStrokeColor();
        this.strokeWidth = options.getStrokeWidth();
    }

    public void setCenter(LatLng center) {
        if (center != null) {
            this.center = center;
            if (host != null) host.onCircleChanged();
        }
    }

    public LatLng getCenter() { return center; }

    public void setRadius(double radius) {
        this.radius = radius;
        if (host != null) host.onCircleChanged();
    }

    public double getRadius() { return radius; }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (host != null) host.onCircleChanged();
    }

    public boolean isVisible() { return visible; }

    public int getFillColor() { return fillColor; }
    public int getStrokeColor() { return strokeColor; }
    public float getStrokeWidth() { return strokeWidth; }

    public void remove() {
        if (host != null) host.onCircleRemoved(this);
    }
}
