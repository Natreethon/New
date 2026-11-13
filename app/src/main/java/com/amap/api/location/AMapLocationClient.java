package com.amap.api.location;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public final class AMapLocationClient {
    private final Context context;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private AMapLocationClientOption option = new AMapLocationClientOption();
    private AMapLocationListener listener;

    public static void updatePrivacyShow(Context context, boolean isContains, boolean isShow) {}
    public static void updatePrivacyAgree(Context context, boolean isAgree) {}

    public AMapLocationClient(Context context) {
        this.context = context != null ? context.getApplicationContext() : null;
    }

    public void setLocationOption(AMapLocationClientOption option) {
        if (option != null) this.option = option;
    }

    public void setLocationListener(AMapLocationListener listener) {
        this.listener = listener;
    }

    public void startLocation() {
        handler.postDelayed(() -> {
            if (listener == null) return;
            AMapLocation fix = new AMapLocation("AMAP");
            fix.setLatitude(13.7563);
            fix.setLongitude(100.5018);
            fix.setAccuracy(12.0f);
            fix.setBearing(90.0f);
            fix.setSpeed(0.0f);
            fix.setTime(System.currentTimeMillis());
            fix.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
            fix.setErrorCode(0);
            listener.onLocationChanged(fix);
        }, 150L);
    }

    public void stopLocation() {
        handler.removeCallbacksAndMessages(null);
    }

    public void onDestroy() {
        stopLocation();
        listener = null;
    }
}
