package com.amap.api.services.geocoder;

public final class RegeocodeAddress {
    private final String formatAddress;

    public RegeocodeAddress(String formatAddress) {
        this.formatAddress = formatAddress;
    }

    public String getFormatAddress() { return formatAddress; }
}
