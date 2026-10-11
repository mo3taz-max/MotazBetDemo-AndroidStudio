package com.motazbet.demo;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.text.InputType;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private final int BG = Color.rgb(16,17,20);
    private final int PANEL = Color.rgb(32,34,40);
    private final int GOLD = Color.rgb(216,180,90);
    private final int WHITE = Color.WHITE;
    private final int MUTED = Color.rgb(175,178,187);
    private LinearLayout root;
    private final ArrayList<String> demoRequests = new ArrayList<>();
    private final String walletNumber = "01016989069";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        showHome();
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setPadding(0, 7, 0, 7);
        return t;
    }

    private LinearLayout column() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(20, 16, 20, 24);
        l.setBackgroundColor(BG);
        return l;
    }

    private void setup(String title) {
        ScrollView scroll = new ScrollView(this);
        root = column();
        TextView brand = text("MOTAZ BET  •  DEMO", 13, GOLD, true);
        root.addView(brand);
        TextView heading = text(title, 26, WHITE, true);
        heading.setPadding(0, 8, 0, 18);
        root.addView(heading);
        scroll.setFillViewport(true);
        scroll.addView(root);
        setContentView(scroll);
    }

    private TextView fieldLabel(String s) {
        TextView t = text(s, 14, MUTED, true);
        root.addView(t);
        return t;
    }

    private EditText input(String hint, int type) {
        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setTextColor(WHITE);
        e.setHintTextColor(MUTED);
        e.setHint(hint);
        e.setInputType(type);
        e.setPadding(14, 10, 14, 10);
        e.setBackgroundColor(PANEL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 3, 0, 12);
        root.addView(e, lp);
        return e;
    }

    private Button button(String label, boolean primary, Runnable action) {
        Button b = new Button(this);
        b.setText(label);
        b.setAllCaps(false);
        b.setTextColor(primary ? BG : GOLD);
        b.setBackgroundTintList(android.content.res.ColorStateList.valueOf(primary ? GOLD : PANEL));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 7, 0, 7);
        root.addView(b, lp);
        b.setOnClickListener(v -> action.run());
        return b;
    }

    private void note(String s) {
        TextView t = text(s, 12, MUTED, false);
        t.setPadding(0, 12, 0, 12);
        root.addView(t);
    }

    private void showHome() {
        setup("توقعات رياضية");
        note("هذه نسخة تجريبية فقط — لا توجد مراهنات أو تحويلات مالية حقيقية.");
        addCard("كرة القدم", "فريق النسر  •  فريق الصقور", "توقع تجريبي: 2 - 1");
        addCard("كرة القدم", "فريق المدينة  •  فريق الساحل", "توقع تجريبي: 1 - 1");
        button("المحفظة التجريبية", true, this::showWallet);
        button("لوحة المسؤول التجريبية", false, this::showAdmin);
    }

    private void addCard(String title, String teams, String result) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(16, 12, 16, 12);
        card.setBackgroundColor(PANEL);
        TextView a = text(title, 12, GOLD, true);
        TextView b = text(teams, 17, WHITE, true);
        TextView c = text(result, 13, MUTED, false);
        card.addView(a); card.addView(b); card.addView(c);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 6, 0, 10);
        root.addView(card, lp);
    }

    private void showWallet() {
        setup("طلب إيداع تجريبي");
        note("لا ترسل أموالًا حقيقية. الرقم أدناه معروض كنموذج واجهة فقط.");
        fieldLabel("رقم المحفظة المعروض");
        TextView num = text(walletNumber, 22, GOLD, true);
        root.addView(num);
        button("نسخ الرقم", false, () -> {
            android.content.ClipboardManager cm = (android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(android.content.ClipData.newPlainText("رقم تجريبي", walletNumber));
            Toast.makeText(this, "تم نسخ الرقم", Toast.LENGTH_SHORT).show();
        });
        fieldLabel("المبلغ التجريبي");
        EditText amount = input("مثال: 100", InputType.TYPE_CLASS_NUMBER);
        fieldLabel("رقم المُرسِل (بيانات تجريبية فقط)");
        EditText sender = input("01xxxxxxxxx", InputType.TYPE_CLASS_PHONE);
        fieldLabel("إثبات التحويل");
        note("رفع الصور غير مفعّل في هذه النسخة التجريبية؛ لا ترفق مستندات أو بيانات مالية حقيقية.");
        button("إرسال طلب تجريبي", true, () -> {
            if (amount.getText().toString().trim().isEmpty() ||
                sender.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "أدخل بيانات تجريبية لإكمال النموذج", Toast.LENGTH_SHORT).show();
                return;
            }
            demoRequests.add("طلب تجريبي • مبلغ: " + amount.getText().toString()
                + " • رقم مُدخل: " + sender.getText().toString() + " • الحالة: قيد المراجعة");
            Toast.makeText(this, "تم حفظ الطلب داخل الجلسة التجريبية فقط", Toast.LENGTH_LONG).show();
        });
        button("رجوع للرئيسية", false, this::showHome);
    }

    private void showAdmin() {
        setup("لوحة المسؤول التجريبية");
        note("هذه لوحة محلية للعرض فقط. لا يوجد تسجيل دخول آمن أو تحقق من أي تحويل.");
        if (demoRequests.isEmpty()) {
            note("لا توجد طلبات تجريبية في هذه الجلسة.");
        } else {
            for (String req : new ArrayList<>(demoRequests)) {
                addCard("طلب وارد", req, "الحالة: قيد المراجعة");
            }
            button("مسح الطلبات التجريبية", false, () -> {
                demoRequests.clear();
                showAdmin();
            });
        }
        button("رجوع للرئيسية", true, this::showHome);
    }
}
