package com.amap.api.services.core;

public final class PoiItem {
    private final String poiId;
    private final LatLonPoint latLonPoint;
    private final String title;
    private final String snippet;

    public PoiItem(String id, LatLonPoint point, String title, String snippet) {
        this.poiId = id;
        this.latLonPoint = point;
        this.title = title;
        this.snippet = snippet;
    }

    public String getPoiId() { return poiId; }
    public LatLonPoint getLatLonPoint() { return latLonPoint; }
    public String getTitle() { return title; }
    public String getSnippet() { return snippet; }
}
