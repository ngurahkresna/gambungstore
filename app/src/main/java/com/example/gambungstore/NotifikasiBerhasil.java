package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.gambungstore.progressbar.ProgressBarGambung;

public class NotifikasiBerhasil extends AppCompatActivity {

    private ImageView mBackArrow;
    private ProgressBarGambung progressBarGambung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_berhasil);

        progressBarGambung = new ProgressBarGambung(this);

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(NotifikasiBerhasil.this,jiCashHomeActivity.class);
                startActivity(intent);
                progressBarGambung.startProgressBarGambung();
            }
        });
    }

    public void btnHome(View view) {
        Intent intent = new Intent(NotifikasiBerhasil.this,homeActivity.class);
        startActivity(intent);
        progressBarGambung.startProgressBarGambung();
    }
}
