package com.amap.api.maps;

import android.content.Context;

public final class MapsInitializer {
    private static boolean privacyAgreed = false;
    private static String countryCode = "TH";
    private static int languageType = 7;

    private MapsInitializer() {}

    public static void updatePrivacyShow(Context context, boolean isContains, boolean isShow) {
        // Privacy dialog state recorded
    }

    public static void updatePrivacyAgree(Context context, boolean isAgree) {
        privacyAgreed = isAgree;
    }

    public static void updateCountryCode(String code) {
        if (code != null && !code.isEmpty()) countryCode = code;
    }

    public static void initRegionLanguageType(int type) {
        languageType = type;
    }

    public static void loadWorldVectorMap(boolean load) {
        // World vector map flag
    }

    public static boolean isPrivacyAgreed() { return privacyAgreed; }
    public static String getCountryCode() { return countryCode; }
    public static int getLanguageType() { return languageType; }
}
