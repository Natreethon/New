package com.amap.api.navi;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public final class AMapNavi {
    private static AMapNavi instance;
    private final List<AMapNaviListener> listeners = new ArrayList<>();

    public static final class SimpleNaviInfo {
        private final String nextRoadName;
        private final int curStepRetainDistance;
        private final int pathRetainDistance;
        private final int pathRetainTime;

        public SimpleNaviInfo(String road, int nextM, int remainM, int remainS) {
            this.nextRoadName = road;
            this.curStepRetainDistance = nextM;
            this.pathRetainDistance = remainM;
            this.pathRetainTime = remainS;
        }

        public String getNextRoadName() { return nextRoadName; }
        public int getCurStepRetainDistance() { return curStepRetainDistance; }
        public int getPathRetainDistance() { return pathRetainDistance; }
        public int getPathRetainTime() { return pathRetainTime; }
    }

    private AMapNavi() {}

    public static synchronized AMapNavi getInstance(Context context) {
        if (instance == null) {
            instance = new AMapNavi();
        }
        return instance;
    }

    public void addAMapNaviListener(AMapNaviListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public List<AMapNaviListener> getListeners() {
        return listeners;
    }
}
