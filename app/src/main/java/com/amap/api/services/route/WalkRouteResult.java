package com.amap.api.services.route;

import java.util.ArrayList;
import java.util.List;

public final class WalkRouteResult {
    private final List<WalkPath> paths;

    public WalkRouteResult(List<WalkPath> paths) {
        this.paths = paths != null ? paths : new ArrayList<>();
    }

    public List<WalkPath> getPaths() {
        return paths;
    }
}
