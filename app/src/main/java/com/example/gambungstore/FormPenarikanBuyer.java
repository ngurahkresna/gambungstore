package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gambungstore.progressbar.ProgressBarGambung;

public class FormPenarikanBuyer extends AppCompatActivity {

    private EditText mJumlahjicash, mNomorrekening, mAtasnama, mPenyediajasa;
    private ImageView mBackArrow;
    private Button mButtonKonfirmasiFormPenarikan;

    private ProgressBarGambung progressBarGambung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_penarikan_buyer);

        progressBarGambung = new ProgressBarGambung(this);
        progressBarGambung.startProgressBarGambung();

        this.getXml();

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(FormPenarikanBuyer.this, jiCashHomeActivity.class);
                startActivity(intent);
                progressBarGambung.startProgressBarGambung();
            }
        });

        mButtonKonfirmasiFormPenarikan = findViewById(R.id.buttonKonfirmasiFormPenarikan);
        mButtonKonfirmasiFormPenarikan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mJumlahjicash.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Jumlah Jicash Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mNomorrekening.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Nomor Rekening Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mAtasnama.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Atas Nama Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mPenyediajasa.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Pastikan Penyedia Jasa Field Terisi", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent = new Intent(FormPenarikanBuyer.this, CheckoutPenarikanBuyerProses.class);
                    startActivity(intent);
                    progressBarGambung.startProgressBarGambung();
                }
            }
        });
        progressBarGambung.endProgressBarGambung();
    }

    private void getXml() {
        this.mJumlahjicash = findViewById(R.id.jumlahjicash);
        this.mNomorrekening = findViewById(R.id.nomorrekening);
        this.mAtasnama = findViewById(R.id.atasnama);
        this.mPenyediajasa = findViewById(R.id.penyediajasa);
    }
}