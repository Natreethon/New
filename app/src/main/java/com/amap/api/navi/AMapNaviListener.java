package com.amap.api.navi;

public interface AMapNaviListener {
    void onInitNaviFailure();
    void onCalculateRouteSuccess(int[] routeIds);
    void onCalculateRouteFailure(Object result);
    void onReCalculateRouteForYaw();
    void onReCalculateRouteForTrafficJam();
    void onStartNavi(int type);
    void onNaviInfoUpdate(Object naviInfo);
    void onArriveDestination();
    void onEndEmulatorNavi();
    void onStopNavi();
    void onLocationChange(Object location);
}
