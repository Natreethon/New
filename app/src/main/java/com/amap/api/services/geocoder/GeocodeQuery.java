package com.amap.api.services.geocoder;

public final class GeocodeQuery {
    private final String locationName;
    private final String city;

    public GeocodeQuery(String locationName, String city) {
        this.locationName = locationName != null ? locationName : "";
        this.city = city != null ? city : "";
    }

    public String getLocationName() { return locationName; }
    public String getCity() { return city; }
}
