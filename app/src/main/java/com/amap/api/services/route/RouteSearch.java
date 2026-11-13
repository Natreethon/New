package com.amap.api.services.route;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RouteSearch {
    public static final int WALK_DEFAULT = 0;
    public static final int DRIVING_SINGLE_DEFAULT = 0;
    public static final int DRIVING_SINGLE_SAVE_MONEY = 1;
    public static final int DRIVING_SINGLE_NO_HIGHWAY = 3;
    public static final int DRIVING_SINGLE_AVOID_CONGESTION = 4;
    public static final int DRIVING_SINGLE_NO_HIGHWAY_SAVE_MONEY = 6;
    public static final int DRIVING_SINGLE_SAVE_MONEY_AVOID_CONGESTION = 7;
    public static final int DRIVING_SINGLE_NO_HIGHWAY_SAVE_MONEY_AVOID_CONGESTION = 9;

    public interface OnRouteSearchListener {
        void onBusRouteSearched(BusRouteResult result, int errorCode);
        void onDriveRouteSearched(DriveRouteResult result, int errorCode);
        void onWalkRouteSearched(WalkRouteResult result, int errorCode);
        void onRideRouteSearched(RideRouteResult result, int errorCode);
    }

    public static final class FromAndTo {
        private final LatLonPoint from;
        private final LatLonPoint to;

        public FromAndTo(LatLonPoint from, LatLonPoint to) {
            this.from = from;
            this.to = to;
        }

        public LatLonPoint getFrom() { return from; }
        public LatLonPoint getTo() { return to; }
    }

    public static final class WalkRouteQuery {
        private final FromAndTo fromAndTo;
        private final int mode;

        public WalkRouteQuery(FromAndTo fromAndTo, int mode) {
            this.fromAndTo = fromAndTo;
            this.mode = mode;
        }

        public FromAndTo getFromAndTo() { return fromAndTo; }
        public int getMode() { return mode; }
    }

    public static final class DriveRouteQuery {
        private final FromAndTo fromAndTo;
        private final int mode;
        private final List<LatLonPoint> passedByPoints;
        private final List<List<LatLonPoint>> avoidpolygons;
        private final String avoidRoad;

        public DriveRouteQuery(FromAndTo fromAndTo, int mode, List<LatLonPoint> passedByPoints,
                               List<List<LatLonPoint>> avoidpolygons, String avoidRoad) {
            this.fromAndTo = fromAndTo;
            this.mode = mode;
            this.passedByPoints = passedByPoints;
            this.avoidpolygons = avoidpolygons;
            this.avoidRoad = avoidRoad;
        }

        public FromAndTo getFromAndTo() { return fromAndTo; }
        public int getMode() { return mode; }
    }

    private final Context context;
    private OnRouteSearchListener listener;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public RouteSearch(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
    }

    public void setRouteSearchListener(OnRouteSearchListener listener) {
        this.listener = listener;
    }

    public void calculateDriveRouteAsyn(DriveRouteQuery query) {
        handler.postDelayed(() -> {
            if (listener == null || query == null || query.getFromAndTo() == null) return;
            LatLonPoint start = query.getFromAndTo().getFrom();
            LatLonPoint end = query.getFromAndTo().getTo();
            if (start == null || end == null) {
                listener.onDriveRouteSearched(new DriveRouteResult(Collections.emptyList()), 1802);
                return;
            }
            float[] dist = new float[1];
            Location.distanceBetween(start.getLatitude(), start.getLongitude(),
                    end.getLatitude(), end.getLongitude(), dist);
            float roadDist = Math.max(450f, dist[0] * 1.28f);
            long durationSec = Math.max(120L, Math.round(roadDist / 9.5f));

            List<DrivePath> paths = new ArrayList<>();
            paths.add(new DrivePath(Collections.singletonList(new DriveStep(buildCurve(start, end, 0.0))),
                    roadDist, durationSec, 0f));
            paths.add(new DrivePath(Collections.singletonList(new DriveStep(buildCurve(start, end, 0.0045))),
                    roadDist * 1.12f, Math.round(durationSec * 0.88f), 50f));
            listener.onDriveRouteSearched(new DriveRouteResult(paths), 1000);
        }, 140L);
    }

    public void calculateWalkRouteAsyn(WalkRouteQuery query) {
        handler.postDelayed(() -> {
            if (listener == null || query == null || query.getFromAndTo() == null) return;
            LatLonPoint start = query.getFromAndTo().getFrom();
            LatLonPoint end = query.getFromAndTo().getTo();
            if (start == null || end == null) {
                listener.onWalkRouteSearched(new WalkRouteResult(Collections.emptyList()), 1802);
                return;
            }
            float[] dist = new float[1];
            Location.distanceBetween(start.getLatitude(), start.getLongitude(),
                    end.getLatitude(), end.getLongitude(), dist);
            float walkDist = Math.max(200f, dist[0] * 1.15f);
            long durationSec = Math.max(90L, Math.round(walkDist / 1.35f));

            WalkPath walkPath = new WalkPath(
                    Collections.singletonList(new WalkStep(buildCurve(start, end, 0.0015))),
                    walkDist, durationSec);
            listener.onWalkRouteSearched(new WalkRouteResult(Collections.singletonList(walkPath)), 1000);
        }, 140L);
    }

    private static List<LatLonPoint> buildCurve(LatLonPoint from, LatLonPoint to, double offset) {
        List<LatLonPoint> pts = new ArrayList<>();
        int segments = 16;
        for (int i = 0; i <= segments; i++) {
            double t = (double) i / segments;
            double arc = Math.sin(t * Math.PI) * offset;
            double lat = from.getLatitude() + (to.getLatitude() - from.getLatitude()) * t + arc;
            double lon = from.getLongitude() + (to.getLongitude() - from.getLongitude()) * t - arc * 0.7;
            pts.add(new LatLonPoint(lat, lon));
        }
        return pts;
    }
}
