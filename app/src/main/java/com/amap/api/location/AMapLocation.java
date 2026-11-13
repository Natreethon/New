package com.amap.api.location;

import android.location.Location;

public final class AMapLocation extends Location {
    private int errorCode = 0;

    public AMapLocation(String provider) {
        super(provider);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
