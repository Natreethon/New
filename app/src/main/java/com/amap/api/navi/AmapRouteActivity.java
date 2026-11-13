package com.amap.api.navi;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public final class AmapRouteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String dest = getIntent() != null ? getIntent().getStringExtra("dest_name") : "";
        if (dest == null || dest.isEmpty()) dest = "จุดหมายปลายทาง (Destination)";

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(0xFF101C22);
        int pad = Math.round(24 * getResources().getDisplayMetrics().density);
        root.setPadding(pad, pad, pad, pad);

        TextView title = new TextView(this);
        title.setText("AMAP Turn-by-Turn Navigation");
        title.setTextSize(20);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView sub = new TextView(this);
        sub.setText("\nกำลังนำทางไปยัง: " + dest + "\nตรงไปตามถนนพระรามที่ 1 อีก 350 เมตร\n");
        sub.setTextSize(15);
        sub.setTextColor(0xFFB8CFD4);
        sub.setGravity(Gravity.CENTER);
        root.addView(sub);

        Button exit = new Button(this);
        exit.setText("สิ้นสุดการนำทาง · Exit Navigation");
        exit.setOnClickListener(v -> {
            AmapNaviPage.getInstance().exitRouteActivity();
            finish();
        });
        root.addView(exit);

        setContentView(root);
    }
}
