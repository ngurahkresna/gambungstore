package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class CheckoutPenarikanBuyerProses extends AppCompatActivity {

    private ImageView mBackArrow;
    private TextView mTime;
    private CountDownTimer timerClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_penarikan_buyer_proses);

        mTime = findViewById(R.id.time);
        timerClass = new CountDownTimer(60000 * 60 *24, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String waktu = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                mTime.setText("" +waktu);
            }

            @Override
            public void onFinish() {
                ///Berjalan saat waktu telah selesai atau berhenti
                Toast.makeText(CheckoutPenarikanBuyerProses.this, "Waktu Telah Berakhir", Toast.LENGTH_LONG).show();
                mTime.setText("00:00:00");
                finish();
            }
        }.start();

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,FormPenarikanBuyer.class);
                startActivity(intent);
            }
        });
    }



    public void btnHome(View view) {
        finish();
        Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,homeActivity.class);
        startActivity(intent);
    }


    public void btnKonfirmasipenarikan(View view) {
        Intent intent = new Intent(CheckoutPenarikanBuyerProses.this,NotifikasiBerhasil.class);
        startActivity(intent);
    }

}
