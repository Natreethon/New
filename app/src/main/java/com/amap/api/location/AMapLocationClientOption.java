package com.amap.api.location;

public final class AMapLocationClientOption {
    public enum AMapLocationMode {
        Battery_Saving,
        Device_Sensors,
        Hight_Accuracy
    }

    private AMapLocationMode locationMode = AMapLocationMode.Hight_Accuracy;
    private boolean onceLocation = false;
    private boolean onceLocationLatest = false;
    private boolean gpsFirst = false;
    private long gpsFirstTimeout = 3000L;
    private boolean needAddress = false;

    public AMapLocationClientOption setLocationMode(AMapLocationMode mode) {
        this.locationMode = mode;
        return this;
    }

    public AMapLocationClientOption setOnceLocation(boolean once) {
        this.onceLocation = once;
        return this;
    }

    public AMapLocationClientOption setOnceLocationLatest(boolean latest) {
        this.onceLocationLatest = latest;
        return this;
    }

    public AMapLocationClientOption setGpsFirst(boolean gpsFirst) {
        this.gpsFirst = gpsFirst;
        return this;
    }

    public AMapLocationClientOption setGpsFirstTimeout(long timeout) {
        this.gpsFirstTimeout = timeout;
        return this;
    }

    public AMapLocationClientOption setNeedAddress(boolean needAddress) {
        this.needAddress = needAddress;
        return this;
    }

    public AMapLocationMode getLocationMode() { return locationMode; }
    public boolean isOnceLocation() { return onceLocation; }
    public boolean isOnceLocationLatest() { return onceLocationLatest; }
    public boolean isGpsFirst() { return gpsFirst; }
    public long getGpsFirstTimeout() { return gpsFirstTimeout; }
    public boolean isNeedAddress() { return needAddress; }
}
