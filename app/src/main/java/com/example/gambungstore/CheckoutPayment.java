package com.example.gambungstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pl.aprilapps.easyphotopicker.EasyImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutPayment extends AppCompatActivity {
    private static final String TAG = "CheckoutPayment";
    
    TextView mDiscountPrice,mProductPrice, mTotalPrice,mExpeditionPrice;
    Button mSubmitButtom,mBackButton;
    TextView mTime;

    ProgressBarGambung progressbar = new ProgressBarGambung(this);

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        long timeall = 86400000;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String created = getIntent().getStringExtra("created_at");
        LocalDateTime dateTime = LocalDateTime.parse(created, formatter);
        LocalDateTime today = LocalDateTime.now();
        Duration duration = Duration.between(today, dateTime);
        long diff = 0;
        if (duration.toMillis() < 0){
            diff = timeall + duration.toMillis();
        }else{
            diff = 0;
        }

        Log.d(TAG, "onCreate: "+duration.toMinutes()+":"+duration.toMillis()+":");

        countdownTime(diff);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CheckoutPayment.this,homeActivity.class);
                startActivity(intent);
            }
        });

        mSubmitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    private void countdownTime(long diff){
        CountDownTimer timer = new CountDownTimer(diff,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
                Log.d(TAG, "onTick: "+diff);
                if (diff == 0){
                    mTime.setText("00:00:00");
                }else{
                    mTime.setText(Integer.toString(hours)+":"+Integer.toString(minutes)+":"+Integer.toString(seconds));
                }

            }
            @Override
            public void onFinish() {
                Log.d(TAG, "onFinish: sudah habis");
                mTime.setText("00:00:00");
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
        Log.d(TAG, "onActivityResult: "+requestCode+" "+resultCode+" "+data);
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new EasyImage.Callbacks() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource imageSource, int i) {
                Toast.makeText(CheckoutPayment.this, "Gagal Memilih Gambar", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Log.d(TAG, "onImagePickerError: "+e.getMessage());
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                File file = new File(String.valueOf(imageFile));
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                String imageName = file.getName();

                MultipartBody.Part bodyJicash = MultipartBody.Part.createFormData("topup_proof", imageName, requestFile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("proof_image", imageName, requestFile);

                boolean jicash = false;
                if (getIntent().getStringExtra("jicash") != null) {
                    jicash = true;
                }

                boolean cashback = false;
                if (getIntent().getStringExtra("discountType") != null) {
                    cashback = true;
                }

                Log.d(TAG, "onImagePicked: "+jicash+" "+cashback);

                if (jicash){
                    uploadProofJicash(bodyJicash,getIntent().getIntExtra("ammount",0));
                }else{
                    uploadProof(body);
                    if (cashback){
                        uploadProofJicash(bodyJicash,getIntent().getIntExtra("discountPrice",0));
                    }
                }
            }

            @Override
            public void onCanceled(EasyImage.ImageSource imageSource, int i) {

            }
        });
    }

    private void uploadProof(MultipartBody.Part image){

        progressbar.startProgressBarGambung();

        RequestBody transaction_code =
                RequestBody.create(MediaType.parse("text/plain"), getIntent().getStringExtra("transaction_code"));
        RequestBody username =
                RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getRegisteredUsername(this));

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> callUploadProof = service.uploadProof(
                transaction_code,
                image,
                username
        );
        callUploadProof.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: "+response.raw());
                Intent intent = new Intent(CheckoutPayment.this, CheckoutDone.class);
                startActivity(intent);
                finish();
                progressbar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    private void uploadProofJicash(MultipartBody.Part image,int value){

        progressbar.startProgressBarGambung();

        RequestBody ammount =
                RequestBody.create(MediaType.parse("text/plain"), String.valueOf(value));
        RequestBody username =
                RequestBody.create(MediaType.parse("text/plain"), SharedPreference.getRegisteredUsername(this));

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> callUploadProof = service.uploadProofJicash(
                ammount,
                image,
                username
        );
        callUploadProof.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: "+response.raw());
                Intent intent = new Intent(CheckoutPayment.this, CheckoutDone.class);
                startActivity(intent);
                finish();
                progressbar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }


}
