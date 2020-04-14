package com.example.gambungstore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class jicashAlert extends AppCompatActivity {

    private static final String TAG = "jicashAlert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jicash_alert);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height/2.2));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void mengertiButton(View view) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime today = LocalDateTime.now();
        Intent intent = new Intent(jicashAlert.this, CheckoutPayment.class);
        int ammount = getIntent().getIntExtra("ammount",0);
        Log.d(TAG, "mengertiButton: "+ammount);
        intent.putExtra("productPrice", ammount);
        intent.putExtra("discountPrice", 0);
        intent.putExtra("expeditionPrice",0);
        intent.putExtra("grandTotalPrice",ammount);
        intent.putExtra("transaction_code", 0);
        intent.putExtra("created_at",formatter.format(today).toString());
        intent.putExtra("jicash", "jicash");
        startActivity(intent);
        finish();
    }
}
