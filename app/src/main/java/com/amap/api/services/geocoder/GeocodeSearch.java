package com.amap.api.services.geocoder;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class GeocodeSearch {
    public static final String AMAP = "autonavi";

    public interface OnGeocodeSearchListener {
        void onRegeocodeSearched(RegeocodeResult result, int rCode);
        void onGeocodeSearched(GeocodeResult result, int rCode);
    }

    private final Context context;
    private OnGeocodeSearchListener listener;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public GeocodeSearch(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
    }

    public void setOnGeocodeSearchListener(OnGeocodeSearchListener listener) {
        this.listener = listener;
    }

    public void getFromLocationNameAsyn(GeocodeQuery query) {
        handler.postDelayed(() -> {
            if (listener == null) return;
            List<GeocodeAddress> list = new ArrayList<>();
            String name = query != null ? query.getLocationName() : "Bangkok";
            list.add(new GeocodeAddress(new LatLonPoint(13.7465, 100.5347),
                    name + " · เขตปทุมวัน กรุงเทพมหานคร 10330"));
            list.add(new GeocodeAddress(new LatLonPoint(13.7563, 100.5018),
                    name + " · เขตพระนคร กรุงเทพมหานคร 10200"));
            listener.onGeocodeSearched(new GeocodeResult(query, list), 1000);
        }, 120L);
    }

    public void getFromLocationAsyn(RegeocodeQuery query) {
        handler.postDelayed(() -> {
            if (listener == null) return;
            LatLonPoint pt = query != null ? query.getPoint() : new LatLonPoint(13.7563, 100.5018);
            String formatted = String.format(Locale.US,
                    "ถนนพระรามที่ 1 แขวงปทุมวัน เขตปทุมวัน กรุงเทพมหานคร (%.5f, %.5f)",
                    pt.getLatitude(), pt.getLongitude());
            RegeocodeResult result = new RegeocodeResult(query, new RegeocodeAddress(formatted));
            listener.onRegeocodeSearched(result, 1000);
        }, 120L);
    }
}
