package com.amap.api.navi;

import android.content.Context;
import com.amap.api.maps.model.Poi;
import java.util.List;

public final class AmapNaviParams {
    private final Poi start;
    private final List<Poi> ways;
    private final Poi end;
    private final AmapNaviType naviType;
    private final AmapPageType pageType;

    public AmapNaviParams(Poi start, List<Poi> ways, Poi end, AmapNaviType naviType, AmapPageType pageType) {
        this.start = start;
        this.ways = ways;
        this.end = end;
        this.naviType = naviType;
        this.pageType = pageType;
    }

    public Poi getEnd() { return end; }
    public void setUseInnerVoice(boolean v) {}
    public void setTrafficEnabled(boolean v) {}
    public void setNeedCalculateRouteWhenPresent(boolean v) {}
    public void setNeedDestroyDriveManagerInstanceWhenNaviExit(boolean v) {}
    public void setCarDirectionMode(Context c, int mode) {}
    public void setScaleAutoChangeEnable(Context c, boolean enable) {}
    public void setDayAndNightMode(Context c, int mode) {}
    public void setNaviMode(int mode) {}
    public void setShowExitNaviDialog(boolean v) {}
    public void setShowCrossImage(boolean v) {}
    public void setSecondActionVisible(boolean v) {}
    public void setMultipleRouteNaviMode(boolean v) {}
}
