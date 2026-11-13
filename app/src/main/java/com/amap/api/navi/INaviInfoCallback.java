package com.amap.api.navi;

public interface INaviInfoCallback {
    void onExitPage(int pageType);
    void onGetNavigationText(String text);
    void onLocationChange(Object location);
}
