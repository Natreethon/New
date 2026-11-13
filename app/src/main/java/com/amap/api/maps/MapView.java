package com.amap.api.maps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.FrameLayout;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.Polyline;
import java.util.List;

public class MapView extends FrameLayout implements AMap.RendererHost {
    private final AMap aMap;
    private final Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint roadPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint highwayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint riverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint buildingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint trafficPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint polylinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint circleFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint circleStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint infoBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint infoTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final GestureDetector gestureDetector;
    private final ScaleGestureDetector scaleDetector;

    private static final double[][] RIVER_POINTS = {
        {13.8200, 100.5120}, {13.7950, 100.5060}, {13.7750, 100.4940},
        {13.7563, 100.4895}, {13.7420, 100.5030}, {13.7220, 100.5110},
        {13.7020, 100.4980}, {13.6800, 100.5250}
    };

    private static final double[][][] ROADS = {
        {{13.7563, 100.4500}, {13.7563, 100.5018}, {13.7465, 100.5347}, {13.7370, 100.5604}, {13.7190, 100.5850}},
        {{13.8150, 100.5530}, {13.7750, 100.5420}, {13.7465, 100.5347}, {13.7260, 100.5280}, {13.6950, 100.5180}},
        {{13.7890, 100.4700}, {13.7650, 100.5150}, {13.7480, 100.5680}, {13.7420, 100.6200}},
        {{13.7250, 100.4650}, {13.7280, 100.5110}, {13.7320, 100.5460}, {13.7350, 100.5900}}
    };

    private static final Object[][] LANDMARKS = {
        {13.7563, 100.5018, "พระบรมมหาราชวัง · Bangkok"},
        {13.7465, 100.5347, "สยามพารากอน · Siam Paragon"},
        {13.7370, 100.5604, "อโศก · Sukhumvit"},
        {13.7262, 100.5141, "ไอคอนสยาม · ICONSIAM"},
        {13.8000, 100.5510, "จตุจักร · Chatuchak"},
        {13.7442, 100.5443, "เซ็นทรัลเวิลด์ · CentralWorld"},
        {13.6900, 100.7501, "ท่าอากาศยานสุวรรณภูมิ · BKK"}
    };

    public MapView(Context context) {
        this(context, null);
    }

    public MapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        this.aMap = new AMap(this);

        roadPaint.setStyle(Paint.Style.STROKE);
        roadPaint.setStrokeCap(Paint.Cap.ROUND);
        roadPaint.setStrokeJoin(Paint.Join.ROUND);

        highwayPaint.setStyle(Paint.Style.STROKE);
        highwayPaint.setStrokeCap(Paint.Cap.ROUND);
        highwayPaint.setStrokeJoin(Paint.Join.ROUND);

        riverPaint.setStyle(Paint.Style.STROKE);
        riverPaint.setStrokeCap(Paint.Cap.ROUND);
        riverPaint.setStrokeJoin(Paint.Join.ROUND);

        trafficPaint.setStyle(Paint.Style.STROKE);
        trafficPaint.setStrokeCap(Paint.Cap.ROUND);

        polylinePaint.setStyle(Paint.Style.STROKE);
        polylinePaint.setStrokeCap(Paint.Cap.ROUND);
        polylinePaint.setStrokeJoin(Paint.Join.ROUND);

        circleFillPaint.setStyle(Paint.Style.FILL);
        circleStrokePaint.setStyle(Paint.Style.STROKE);

        labelPaint.setTextSize(dp(11.5f));
        infoTextPaint.setTextSize(dp(12.5f));
        infoTextPaint.setColor(Color.WHITE);

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                aMap.moveCamera(CameraUpdateFactory.scrollBy(distanceX, distanceY));
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                AMap.OnMapLongClickListener listener = aMap.getOnMapLongClickListener();
                if (listener != null) {
                    LatLng point = aMap.getProjection().fromScreenLocation(
                            new Point(Math.round(e.getX()), Math.round(e.getY())));
                    listener.onMapLongClick(point);
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                float tx = e.getX();
                float ty = e.getY();
                List<Marker> markers = aMap.getMarkers();
                for (int i = markers.size() - 1; i >= 0; i--) {
                    Marker m = markers.get(i);
                    if (!m.isVisible() || m.getPosition() == null) continue;
                    Point p = aMap.getProjection().toScreenLocation(m.getPosition());
                    float dx = tx - p.x;
                    float dy = ty - p.y;
                    if (dx * dx + dy * dy <= dp(32) * dp(32)) {
                        AMap.OnMarkerClickListener clickListener = aMap.getOnMarkerClickListener();
                        if (clickListener != null && clickListener.onMarkerClick(m)) {
                            return true;
                        }
                        m.showInfoWindow();
                        return true;
                    }
                }
                return false;
            }
        });

        scaleDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float factor = detector.getScaleFactor();
                if (factor > 0.01f) {
                    float delta = (float) (Math.log(factor) / Math.log(2.0));
                    aMap.moveCamera(CameraUpdateFactory.zoomBy(delta));
                }
                return true;
            }
        });
    }

    public AMap getMap() {
        return aMap;
    }

    public void onCreate(Bundle savedInstanceState) {}
    public void onResume() { invalidate(); }
    public void onPause() {}
    public void onDestroy() {}
    public void onSaveInstanceState(Bundle outState) {}

    @Override
    public int getWidthPx() {
        return getWidth() > 0 ? getWidth() : 1080;
    }

    @Override
    public int getHeightPx() {
        return getHeight() > 0 ? getHeight() : 1920;
    }

    @Override
    public CameraPosition getCamera() {
        return aMap.getCameraPosition();
    }

    @Override
    public void requestRedraw() {
        postInvalidateOnAnimation();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        AMap.OnMapTouchListener touchListener = aMap.getOnMapTouchListener();
        if (touchListener != null) {
            touchListener.onTouch(event);
        }
        scaleDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) return;

        int type = aMap.getMapType();
        boolean night = (type == AMap.MAP_TYPE_NIGHT);
        boolean satellite = (type == AMap.MAP_TYPE_SATELLITE);

        bgPaint.setColor(satellite ? 0xFF1A2E2B : (night ? 0xFF101C22 : 0xFFEAF0EE));
        canvas.drawRect(0, 0, w, h, bgPaint);

        // Draw subtle coordinate grid
        gridPaint.setColor(satellite ? 0x22FFFFFF : (night ? 0x18FFFFFF : 0x18087F77));
        gridPaint.setStrokeWidth(dp(1));
        int step = Math.max(16, Math.round(dp(64)));
        for (int x = (w / 2) % step; x < w; x += step) {
            canvas.drawLine(x, 0, x, h, gridPaint);
        }
        for (int y = (h / 2) % step; y < h; y += step) {
            canvas.drawLine(0, y, w, y, gridPaint);
        }

        Projection proj = aMap.getProjection();

        // Draw Chao Phraya river
        Path riverPath = new Path();
        for (int i = 0; i < RIVER_POINTS.length; i++) {
            Point p = proj.toScreenLocation(new LatLng(RIVER_POINTS[i][0], RIVER_POINTS[i][1]));
            if (i == 0) riverPath.moveTo(p.x, p.y);
            else riverPath.lineTo(p.x, p.y);
        }
        riverPaint.setColor(satellite ? 0xFF0E3F5C : (night ? 0xFF15384B : 0xFFA3D5E8));
        riverPaint.setStrokeWidth(dp(26));
        canvas.drawPath(riverPath, riverPaint);

        // Draw 3D building footprints when enabled
        if (aMap.isBuildingsEnabled()) {
            buildingPaint.setStyle(Paint.Style.FILL);
            buildingPaint.setColor(night ? 0x33274450 : 0x33C8D6D4);
            for (Object[] lm : LANDMARKS) {
                Point p = proj.toScreenLocation(new LatLng((Double) lm[0], (Double) lm[1]));
                canvas.drawRoundRect(new RectF(p.x - dp(28), p.y - dp(20), p.x + dp(28), p.y + dp(20)),
                        dp(4), dp(4), buildingPaint);
            }
        }

        // Draw major road corridors
        roadPaint.setColor(satellite ? 0x66DDE6E5 : (night ? 0xFF243740 : 0xFFFFFFFF));
        roadPaint.setStrokeWidth(dp(9));
        highwayPaint.setColor(satellite ? 0x99F5C26B : (night ? 0xFF3A525E : 0xFFFCE5B8));
        highwayPaint.setStrokeWidth(dp(12));

        for (int r = 0; r < ROADS.length; r++) {
            Path road = new Path();
            for (int i = 0; i < ROADS[r].length; i++) {
                Point p = proj.toScreenLocation(new LatLng(ROADS[r][i][0], ROADS[r][i][1]));
                if (i == 0) road.moveTo(p.x, p.y);
                else road.lineTo(p.x, p.y);
            }
            canvas.drawPath(road, r < 2 ? highwayPaint : roadPaint);
            if (aMap.isTrafficEnabled()) {
                trafficPaint.setColor(r == 0 ? 0xAA22B573 : (r == 1 ? 0xAAF59E0B : 0xAA10B981));
                trafficPaint.setStrokeWidth(dp(3.5f));
                canvas.drawPath(road, trafficPaint);
            }
        }

        // Draw accuracy circles
        for (Circle circle : aMap.getCircles()) {
            if (!circle.isVisible() || circle.getCenter() == null) continue;
            Point c = proj.toScreenLocation(circle.getCenter());
            float radiusPx = (float) Math.max(dp(12), Math.min(dp(140),
                    circle.getRadius() * Math.pow(2.0, aMap.getCameraPosition().zoom - 13.0) * 0.4));
            circleFillPaint.setColor(circle.getFillColor());
            circleStrokePaint.setColor(circle.getStrokeColor());
            circleStrokePaint.setStrokeWidth(circle.getStrokeWidth());
            canvas.drawCircle(c.x, c.y, radiusPx, circleFillPaint);
            canvas.drawCircle(c.x, c.y, radiusPx, circleStrokePaint);
        }

        // Draw route polylines
        for (Polyline line : aMap.getPolylines()) {
            List<LatLng> pts = line.getPoints();
            if (pts == null || pts.size() < 2) continue;
            Path path = new Path();
            for (int i = 0; i < pts.size(); i++) {
                Point p = proj.toScreenLocation(pts.get(i));
                if (i == 0) path.moveTo(p.x, p.y);
                else path.lineTo(p.x, p.y);
            }
            polylinePaint.setColor(line.getColor());
            polylinePaint.setStrokeWidth(Math.max(dp(5), line.getWidth()));
            canvas.drawPath(path, polylinePaint);
        }

        // Draw map labels
        if (aMap.isMapTextEnabled()) {
            labelPaint.setColor(satellite || night ? 0xFFD9E6E8 : 0xFF284247);
            for (Object[] lm : LANDMARKS) {
                Point p = proj.toScreenLocation(new LatLng((Double) lm[0], (Double) lm[1]));
                if (p.x >= -dp(80) && p.x <= w + dp(80) && p.y >= -dp(40) && p.y <= h + dp(40)) {
                    canvas.drawCircle(p.x, p.y, dp(3.5f), labelPaint);
                    canvas.drawText((String) lm[2], p.x + dp(7), p.y + dp(4), labelPaint);
                }
            }
        }

        // Draw markers
        for (Marker marker : aMap.getMarkers()) {
            if (!marker.isVisible() || marker.getPosition() == null) continue;
            Point p = proj.toScreenLocation(marker.getPosition());
            BitmapDescriptor desc = marker.getIcon();
            Bitmap bmp = desc != null ? desc.getBitmap() : null;
            markerPaint.setAlpha(Math.round(Math.max(0f, Math.min(1f, marker.getAlpha())) * 255f));
            if (bmp != null && !bmp.isRecycled()) {
                canvas.save();
                canvas.translate(p.x, p.y);
                if (marker.getRotateAngle() != 0f) {
                    canvas.rotate(-marker.getRotateAngle());
                }
                float ox = -bmp.getWidth() * marker.getAnchorU();
                float oy = -bmp.getHeight() * marker.getAnchorV();
                canvas.drawBitmap(bmp, ox, oy, markerPaint);
                canvas.restore();
            } else {
                float hue = desc != null ? desc.getHue() : 0f;
                int pinColor = Color.HSVToColor(markerPaint.getAlpha(), new float[]{hue, 0.82f, 0.88f});
                markerPaint.setColor(pinColor);
                canvas.drawCircle(p.x, p.y - dp(12), dp(11), markerPaint);
                markerPaint.setColor(Color.WHITE);
                canvas.drawCircle(p.x, p.y - dp(12), dp(4.5f), markerPaint);
            }

            if (marker.isInfoWindowShown() && marker.getTitle() != null && !marker.getTitle().isEmpty()) {
                String title = marker.getTitle();
                float tw = infoTextPaint.measureText(title);
                RectF box = new RectF(p.x - tw * 0.5f - dp(10), p.y - dp(54),
                        p.x + tw * 0.5f + dp(10), p.y - dp(26));
                infoBgPaint.setColor(0xDD14282D);
                canvas.drawRoundRect(box, dp(8), dp(8), infoBgPaint);
                canvas.drawText(title, p.x - tw * 0.5f, p.y - dp(35), infoTextPaint);
            }
        }
    }

    private float dp(float value) {
        return value * getResources().getDisplayMetrics().density;
    }
}
