package com.amap.api.navi;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

public final class AmapNaviPage {
    private static final AmapNaviPage INSTANCE = new AmapNaviPage();
    private INaviInfoCallback activeCallback;
    private Context lastContext;

    private AmapNaviPage() {}

    public static AmapNaviPage getInstance() {
        return INSTANCE;
    }

    public void showRouteActivity(Context context, AmapNaviParams params, INaviInfoCallback callback) {
        this.activeCallback = callback;
        this.lastContext = context != null ? context.getApplicationContext() : null;
        if (context == null) return;

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            for (AMapNaviListener l : AMapNavi.getInstance(context).getListeners()) {
                l.onCalculateRouteSuccess(new int[]{0});
                l.onStartNavi(1);
                l.onNaviInfoUpdate(new AMapNavi.SimpleNaviInfo("ถนนพระรามที่ 1 · Rama I Rd", 350, 2400, 420));
            }
        }, 120L);

        Intent intent = new Intent(context, AmapRouteActivity.class);
        if (params != null && params.getEnd() != null) {
            intent.putExtra("dest_name", params.getEnd().getName());
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void exitRouteActivity() {
        if (lastContext != null) {
            for (AMapNaviListener l : AMapNavi.getInstance(lastContext).getListeners()) {
                l.onStopNavi();
            }
        }
        if (activeCallback != null) {
            activeCallback.onExitPage(1);
        }
    }
}
