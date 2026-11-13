package com.amap.api.maps.model;

import java.util.ArrayList;
import java.util.List;

public final class Polyline {
    public interface Host {
        void onPolylineRemoved(Polyline polyline);
    }

    private final Host host;
    private final List<LatLng> points;
    private final float width;
    private final int color;

    public Polyline(PolylineOptions options, Host host) {
        this.host = host;
        this.points = new ArrayList<>(options.getPoints());
        this.width = options.getWidth();
        this.color = options.getColor();
    }

    public List<LatLng> getPoints() { return points; }
    public float getWidth() { return width; }
    public int getColor() { return color; }

    public void remove() {
        if (host != null) host.onPolylineRemoved(this);
    }
}
