package com.amap.api.services.geocoder;

public final class RegeocodeResult {
    private final RegeocodeQuery query;
    private final RegeocodeAddress address;

    public RegeocodeResult(RegeocodeQuery query, RegeocodeAddress address) {
        this.query = query;
        this.address = address;
    }

    public RegeocodeQuery getRegeocodeQuery() { return query; }
    public RegeocodeAddress getRegeocodeAddress() { return address; }
}
