package com.amap.api.services.route;

import java.util.ArrayList;
import java.util.List;

public final class DrivePath {
    private final List<DriveStep> steps;
    private final float distance;
    private final long duration;
    private final float tolls;

    public DrivePath(List<DriveStep> steps, float distance, long duration, float tolls) {
        this.steps = steps != null ? steps : new ArrayList<>();
        this.distance = distance;
        this.duration = duration;
        this.tolls = tolls;
    }

    public List<DriveStep> getSteps() { return steps; }
    public float getDistance() { return distance; }
    public long getDuration() { return duration; }
    public float getTolls() { return tolls; }
}
