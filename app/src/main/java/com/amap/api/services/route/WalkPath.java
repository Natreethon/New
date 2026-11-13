package com.amap.api.services.route;

import java.util.ArrayList;
import java.util.List;

public final class WalkPath {
    private final List<WalkStep> steps;
    private final float distance;
    private final long duration;

    public WalkPath(List<WalkStep> steps, float distance, long duration) {
        this.steps = steps != null ? steps : new ArrayList<>();
        this.distance = distance;
        this.duration = duration;
    }

    public List<WalkStep> getSteps() { return steps; }
    public float getDistance() { return distance; }
    public long getDuration() { return duration; }
}
