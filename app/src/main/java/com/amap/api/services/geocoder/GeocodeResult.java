package com.amap.api.services.geocoder;

import java.util.ArrayList;
import java.util.List;

public final class GeocodeResult {
    private final GeocodeQuery query;
    private final List<GeocodeAddress> addresses;

    public GeocodeResult(GeocodeQuery query, List<GeocodeAddress> addresses) {
        this.query = query;
        this.addresses = addresses != null ? addresses : new ArrayList<>();
    }

    public GeocodeQuery getGeocodeQuery() { return query; }
    public List<GeocodeAddress> getGeocodeAddressList() { return addresses; }
}
