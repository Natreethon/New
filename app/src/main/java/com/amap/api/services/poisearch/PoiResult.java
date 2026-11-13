package com.amap.api.services.poisearch;

import com.amap.api.services.core.PoiItem;
import java.util.ArrayList;

public final class PoiResult {
    private final ArrayList<PoiItem> pois;

    public PoiResult(ArrayList<PoiItem> pois) {
        this.pois = pois != null ? pois : new ArrayList<>();
    }

    public ArrayList<PoiItem> getPois() {
        return pois;
    }
}
