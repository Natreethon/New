package com.amap.api.maps.offlinemap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class OfflineMapManager {
    public interface OfflineMapDownloadListener {
        void onDownload(int status, int completeCode, String downName);
        void onCheckUpdate(boolean hasNew, String name);
        void onRemove(boolean success, String name, String describe);
    }

    public interface OfflineLoadedListener {
        void onVerifyComplete();
    }

    private final Context context;
    private final OfflineMapDownloadListener downloadListener;
    private OfflineLoadedListener loadedListener;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ArrayList<OfflineMapCity> allCities = new ArrayList<>();

    public OfflineMapManager(Context context, OfflineMapDownloadListener listener) {
        this.context = context.getApplicationContext();
        this.downloadListener = listener;
        allCities.add(new OfflineMapCity("กรุงเทพมหานคร (Bangkok)", "TH-10", 48L * 1024 * 1024));
        allCities.add(new OfflineMapCity("เชียงใหม่ (Chiang Mai)", "TH-50", 29L * 1024 * 1024));
        allCities.add(new OfflineMapCity("ภูเก็ต (Phuket)", "TH-83", 19L * 1024 * 1024));
        allCities.add(new OfflineMapCity("ชลบุรี · พัทยา (Chon Buri)", "TH-20", 24L * 1024 * 1024));
        allCities.add(new OfflineMapCity("ขอนแก่น (Khon Kaen)", "TH-40", 21L * 1024 * 1024));
        allCities.add(new OfflineMapCity("นครราชสีมา (Nakhon Ratchasima)", "TH-30", 26L * 1024 * 1024));
    }

    public void setOnOfflineLoadedListener(OfflineLoadedListener listener) {
        this.loadedListener = listener;
        if (listener != null) {
            handler.post(listener::onVerifyComplete);
        }
    }

    public ArrayList<OfflineMapCity> getOfflineMapCityList() {
        return new ArrayList<>(allCities);
    }

    public ArrayList<OfflineMapCity> getDownloadOfflineMapCityList() {
        Set<String> codes = prefs().getStringSet("downloaded_codes", new HashSet<>());
        ArrayList<OfflineMapCity> out = new ArrayList<>();
        for (OfflineMapCity c : allCities) {
            if (codes.contains(c.getCode()) || codes.contains(c.getCity())) {
                out.add(c);
            }
        }
        return out;
    }

    public void downloadByCityCode(String citycode) {
        OfflineMapCity target = null;
        for (OfflineMapCity c : allCities) {
            if (c.getCode().equalsIgnoreCase(citycode)) {
                target = c;
                break;
            }
        }
        triggerDownload(target != null ? target.getCity() : citycode, citycode);
    }

    public void downloadByCityName(String cityname) {
        OfflineMapCity target = null;
        for (OfflineMapCity c : allCities) {
            if (c.getCity().equalsIgnoreCase(cityname)) {
                target = c;
                break;
            }
        }
        triggerDownload(cityname, target != null ? target.getCode() : cityname);
    }

    private void triggerDownload(String cityName, String code) {
        Set<String> codes = new HashSet<>(prefs().getStringSet("downloaded_codes", new HashSet<>()));
        codes.add(code);
        codes.add(cityName);
        prefs().edit().putStringSet("downloaded_codes", codes).apply();
        if (downloadListener != null) {
            handler.postDelayed(() -> downloadListener.onDownload(0, 50, cityName), 120);
            handler.postDelayed(() -> downloadListener.onDownload(4, 100, cityName), 300);
        }
    }

    public void pause() {}

    public void stop() {}

    public void remove(String name) {
        Set<String> codes = new HashSet<>(prefs().getStringSet("downloaded_codes", new HashSet<>()));
        codes.remove(name);
        for (OfflineMapCity c : allCities) {
            if (c.getCity().equalsIgnoreCase(name)) {
                codes.remove(c.getCode());
            }
        }
        prefs().edit().putStringSet("downloaded_codes", codes).apply();
        if (downloadListener != null) {
            handler.post(() -> downloadListener.onRemove(true, name, "Removed"));
        }
    }

    public void destroy() {
        handler.removeCallbacksAndMessages(null);
    }

    private SharedPreferences prefs() {
        return context.getSharedPreferences("navith_offline_maps", Context.MODE_PRIVATE);
    }
}
