package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class FormPenarikanBuyer extends AppCompatActivity {

    private EditText mJumlahjicash, mNomorrekening, mAtasnama, mPenyediajasa;
    private ImageView mBackArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_penarikan_buyer);

        this.getXml();

        mBackArrow = findViewById(R.id.backArrow);
        mBackArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(FormPenarikanBuyer.this,jiCashHomeActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getXml(){
        this.mJumlahjicash = findViewById(R.id.jumlahjicash);
        this.mNomorrekening = findViewById(R.id.nomorrekening);
        this.mAtasnama = findViewById(R.id.atasnama);
        this.mPenyediajasa = findViewById(R.id.penyediajasa);
    }

    public void Konfirmasiformbtn(View view) {

        if(mJumlahjicash.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Pastikan Jumlah Jicash Field Terisi",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mNomorrekening.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Pastikan Nomor Rekening Field Terisi",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mAtasnama.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Pastikan Atas Nama Field Terisi",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mPenyediajasa.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Pastikan Penyedia Jasa Field Terisi",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Intent intent = new Intent(FormPenarikanBuyer.this, CheckoutPenarikanBuyerProses.class);
            startActivity(intent);
        }
    }
}
