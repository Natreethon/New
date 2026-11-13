package com.amap.api.services.route;

import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.List;

public final class DriveStep {
    private final List<LatLonPoint> polyline;

    public DriveStep(List<LatLonPoint> polyline) {
        this.polyline = polyline != null ? polyline : new ArrayList<>();
    }

    public List<LatLonPoint> getPolyline() {
        return polyline;
    }
}
