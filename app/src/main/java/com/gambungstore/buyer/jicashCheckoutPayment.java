package com.gambungstore.buyer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.aprilapps.easyphotopicker.EasyImage;

public class jicashCheckoutPayment extends AppCompatActivity {
    private static String TAG = "jicashCheckoutPayment";
    TextView timeTv;
    Button submitButton;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jicash_checkout_payment);
        timeTv = findViewById(R.id.time);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jicashCheckoutPayment.this, jicashCheckoutDone.class);
                startActivity(intent);
//                chooseImage();
            }
        });
    }

    private void chooseImage(){
        CharSequence[] item = {"Kamera", "Galeri"};
        AlertDialog.Builder request = new AlertDialog.Builder(this)
                .setTitle("Upload Bukti Pembayaran")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                //Membuka Kamera Untuk Mengambil Gambar
                                EasyImage.openGallery(jicashCheckoutPayment.this, 001);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(jicashCheckoutPayment.this, 002);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }
}
