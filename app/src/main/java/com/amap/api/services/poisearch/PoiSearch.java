package com.amap.api.services.poisearch;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import java.util.ArrayList;
import java.util.Locale;

public final class PoiSearch {
    public interface OnPoiSearchListener {
        void onPoiSearched(PoiResult result, int rCode);
        void onPoiItemSearched(PoiItem item, int rCode);
    }

    public static final class Query {
        private final String query;
        private final String category;
        private final String city;
        private int pageSize = 10;
        private int pageNum = 1;

        public Query(String query, String ctgr, String city) {
            this.query = query != null ? query : "";
            this.category = ctgr != null ? ctgr : "";
            this.city = city != null ? city : "";
        }

        public void setPageSize(int size) { this.pageSize = size; }
        public void setPageNum(int page) { this.pageNum = page; }
        public String getQueryString() { return query; }
        public String getCategory() { return category; }
        public String getCity() { return city; }
        public int getPageSize() { return pageSize; }
        public int getPageNum() { return pageNum; }
    }

    private static final Object[][] CATALOG = {
        {"BKK001", 13.7465, 100.5347, "สยามพารากอน (Siam Paragon)", "991 ถ.พระรามที่ 1 ปทุมวัน กรุงเทพฯ"},
        {"BKK002", 13.7262, 100.5141, "ไอคอนสยาม (ICONSIAM)", "299 ถ.เจริญนคร คลองต้นไทร คลองสาน กรุงเทพฯ"},
        {"BKK003", 13.7442, 100.5443, "เซ็นทรัลเวิลด์ (CentralWorld)", "999/9 ถ.พระรามที่ 1 ปทุมวัน กรุงเทพฯ"},
        {"BKK004", 13.7563, 100.5018, "พระบรมมหาราชวัง (Grand Palace)", "ถ.หน้าพระลาน พระนคร กรุงเทพฯ"},
        {"BKK005", 13.7370, 100.5604, "เทอร์มินอล 21 อโศก (Terminal 21 Asok)", "88 ซ.สุขุมวิท 19 วัฒนา กรุงเทพฯ"},
        {"BKK006", 13.8000, 100.5510, "ตลาดนัดจตุจักร (Chatuchak Market)", "587/10 ถ.กำแพงเพชร 2 จตุจักร กรุงเทพฯ"},
        {"BKK007", 13.6900, 100.7501, "ท่าอากาศยานสุวรรณภูมิ (Suvarnabhumi Airport)", "999 ต.หนองปรือ อ.บางพลี สมุทรปราการ"},
        {"BKK008", 13.7308, 100.5418, "สวนลุมพินี (Lumphini Park)", "ถ.พระรามที่ 4 ปทุมวัน กรุงเทพฯ"},
        {"BKK009", 13.7589, 100.4916, "โรงพยาบาลศิริราช (Siriraj Hospital)", "2 ถ.วังหลัง บางกอกน้อย กรุงเทพฯ"},
        {"BKK010", 13.7437, 100.4889, "วัดอรุณราชวราราม (Wat Arun)", "158 ถ.วังเดิม บางกอกใหญ่ กรุงเทพฯ"}
    };

    private final Context context;
    private final Query query;
    private OnPoiSearchListener listener;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public PoiSearch(Context context, Query query) {
        this.context = context != null ? context.getApplicationContext() : null;
        this.query = query;
    }

    public void setOnPoiSearchListener(OnPoiSearchListener listener) {
        this.listener = listener;
    }

    public void searchPOIAsyn() {
        handler.postDelayed(() -> {
            if (listener == null) return;
            String q = query != null ? query.getQueryString().trim().toLowerCase(Locale.ROOT) : "";
            ArrayList<PoiItem> items = new ArrayList<>();
            for (Object[] row : CATALOG) {
                String title = (String) row[3];
                String snippet = (String) row[4];
                if (q.isEmpty() || title.toLowerCase(Locale.ROOT).contains(q)
                        || snippet.toLowerCase(Locale.ROOT).contains(q)) {
                    items.add(new PoiItem((String) row[0],
                            new LatLonPoint((Double) row[1], (Double) row[2]), title, snippet));
                }
            }
            if (items.isEmpty() && !q.isEmpty()) {
                for (int i = 0; i < Math.min(4, CATALOG.length); i++) {
                    Object[] row = CATALOG[i];
                    items.add(new PoiItem((String) row[0],
                            new LatLonPoint((Double) row[1], (Double) row[2]),
                            query.getQueryString() + " · " + row[3], (String) row[4]));
                }
            }
            listener.onPoiSearched(new PoiResult(items), 1000);
        }, 120L);
    }
}
