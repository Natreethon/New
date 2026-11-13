package com.amap.api.services.route;

import java.util.ArrayList;
import java.util.List;

public final class DriveRouteResult {
    private final List<DrivePath> paths;

    public DriveRouteResult(List<DrivePath> paths) {
        this.paths = paths != null ? paths : new ArrayList<>();
    }

    public List<DrivePath> getPaths() {
        return paths;
    }
}
