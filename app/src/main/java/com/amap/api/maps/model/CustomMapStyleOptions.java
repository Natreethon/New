package com.amap.api.maps.model;

public final class CustomMapStyleOptions {
    private boolean enable = false;
    private String styleDataPath;
    private String styleExtraPath;
    private String styleTexturePath;

    public CustomMapStyleOptions setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public CustomMapStyleOptions setStyleDataPath(String path) {
        this.styleDataPath = path;
        return this;
    }

    public CustomMapStyleOptions setStyleExtraPath(String path) {
        this.styleExtraPath = path;
        return this;
    }

    public CustomMapStyleOptions setStyleTexturePath(String path) {
        this.styleTexturePath = path;
        return this;
    }

    public boolean isEnable() { return enable; }
    public String getStyleDataPath() { return styleDataPath; }
    public String getStyleExtraPath() { return styleExtraPath; }
    public String getStyleTexturePath() { return styleTexturePath; }
}
