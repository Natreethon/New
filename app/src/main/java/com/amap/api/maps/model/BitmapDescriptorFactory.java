package com.amap.api.maps.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public final class BitmapDescriptorFactory {
    public static final float HUE_RED = 0.0f;
    public static final float HUE_ORANGE = 30.0f;
    public static final float HUE_YELLOW = 60.0f;
    public static final float HUE_GREEN = 120.0f;
    public static final float HUE_CYAN = 180.0f;
    public static final float HUE_AZURE = 210.0f;
    public static final float HUE_BLUE = 240.0f;
    public static final float HUE_VIOLET = 270.0f;
    public static final float HUE_MAGENTA = 300.0f;
    public static final float HUE_ROSE = 330.0f;

    private BitmapDescriptorFactory() {}

    public static BitmapDescriptor defaultMarker() {
        return new BitmapDescriptor(null, HUE_RED);
    }

    public static BitmapDescriptor defaultMarker(float hue) {
        return new BitmapDescriptor(null, hue);
    }

    public static BitmapDescriptor fromBitmap(Bitmap image) {
        if (image == null) return defaultMarker();
        Bitmap copy = image.isRecycled() ? null : image.copy(Bitmap.Config.ARGB_8888, false);
        return new BitmapDescriptor(copy, HUE_RED);
    }

    public static BitmapDescriptor fromView(View view) {
        if (view == null) return defaultMarker();
        try {
            int wSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int hSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(wSpec, hSpec);
            int w = Math.max(48, view.getMeasuredWidth());
            int h = Math.max(48, view.getMeasuredHeight());
            view.layout(0, 0, w, h);
            Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            view.draw(canvas);
            return new BitmapDescriptor(bmp, HUE_RED);
        } catch (Exception e) {
            return defaultMarker();
        }
    }
}
