package com.amap.api.services.geocoder;

import com.amap.api.services.core.LatLonPoint;

public final class GeocodeAddress {
    private final LatLonPoint latLonPoint;
    private final String formatAddress;

    public GeocodeAddress(LatLonPoint latLonPoint, String formatAddress) {
        this.latLonPoint = latLonPoint;
        this.formatAddress = formatAddress;
    }

    public LatLonPoint getLatLonPoint() { return latLonPoint; }
    public String getFormatAddress() { return formatAddress; }
}
