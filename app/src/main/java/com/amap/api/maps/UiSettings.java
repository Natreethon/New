package com.amap.api.maps;

public final class UiSettings {
    public interface Listener {
        void onUiSettingsChanged();
    }

    private final Listener listener;
    private boolean zoomControlsEnabled = false;
    private boolean compassEnabled = true;
    private boolean scaleControlsEnabled = true;

    public UiSettings(Listener listener) {
        this.listener = listener;
    }

    public void setZoomControlsEnabled(boolean enabled) {
        this.zoomControlsEnabled = enabled;
        if (listener != null) listener.onUiSettingsChanged();
    }

    public void setCompassEnabled(boolean enabled) {
        this.compassEnabled = enabled;
        if (listener != null) listener.onUiSettingsChanged();
    }

    public void setScaleControlsEnabled(boolean enabled) {
        this.scaleControlsEnabled = enabled;
        if (listener != null) listener.onUiSettingsChanged();
    }

    public boolean isZoomControlsEnabled() { return zoomControlsEnabled; }
    public boolean isCompassEnabled() { return compassEnabled; }
    public boolean isScaleControlsEnabled() { return scaleControlsEnabled; }
}
