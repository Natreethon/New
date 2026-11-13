package com.amap.api.maps.model;

import android.graphics.Bitmap;

public final class BitmapDescriptor {
    private final Bitmap bitmap;
    private final float hue;

    public BitmapDescriptor(Bitmap bitmap, float hue) {
        this.bitmap = bitmap;
        this.hue = hue;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getHue() {
        return hue;
    }
}
