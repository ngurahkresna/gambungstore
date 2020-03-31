package com.example.gambungstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.EasyImage;

public class CheckoutPayment extends AppCompatActivity {
    private static final String TAG = "CheckoutPayment";
    
    TextView mDiscountPrice,mProductPrice, mTotalPrice,mExpeditionPrice;
    Button mSubmitButtom,mBackButton;
    TextView mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_payment);

        mProductPrice = findViewById(R.id.productPrice);
        mDiscountPrice = findViewById(R.id.discountPrice);
        mTotalPrice = findViewById(R.id.totalPrice);
        mExpeditionPrice = findViewById(R.id.expeditionPrice);
        mSubmitButtom = findViewById(R.id.submitButton);
        mBackButton = findViewById(R.id.backButton);
        mTime = findViewById(R.id.time);

        mProductPrice.setText("Rp "+Integer.toString(getIntent().getIntExtra("productPrice",0))+",-");
        mDiscountPrice.setText("Rp "+Integer.toString(getIntent().getIntExtra("discountPrice",0))+",-");
        mTotalPrice.setText("Rp "+Integer.toString(getIntent().getIntExtra("grandTotalPrice",0))+",-");
        mExpeditionPrice.setText("Rp "+Integer.toString(getIntent().getIntExtra("expeditionPrice",0))+",-");

        countdownTime();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSubmitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    private void countdownTime(){
        CountDownTimer timer = new CountDownTimer(86400000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
                mTime.setText(Integer.toString(hours)+":"+Integer.toString(minutes)+":"+Integer.toString(seconds));
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: ");
            }
        }.start();
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
                                EasyImage.openCamera(CheckoutPayment.this, 1);
                                break;
                            case 1:
                                //Membuaka Galeri Untuk Mengambil Gambar
                                EasyImage.openGallery(CheckoutPayment.this, 2);
                                break;
                        }
                    }
                });
        request.create();
        request.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, CheckoutPayment.this,new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                Toast.makeText(CheckoutPayment.this, "Gagal Memilih Gambar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                File file = new File(String.valueOf(imageFile));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                String imageName = file.getName();
                MultipartBody.Part body = MultipartBody.Part.createFormData("image", imageName, requestFile);

                Intent intent = new Intent(CheckoutPayment.this, CheckoutDone.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }
}
