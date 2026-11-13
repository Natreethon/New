package com.amap.api.maps.offlinemap;

public final class OfflineMapCity {
    private final String city;
    private final String code;
    private final long size;

    public OfflineMapCity(String city, String code, long size) {
        this.city = city;
        this.code = code;
        this.size = size;
    }

    public String getCity() { return city; }
    public String getCode() { return code; }
    public long getSize() { return size; }
}
