package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.services.Services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register extends AppCompatActivity {
    private static final String TAG = "registerActivity";

    private EditText mNama,mUsername,mEmail,mPhone,mTglLahir,mAlamat,mPassword,mRepassword,mCity;
    private TextView mBackButton;
    private Button mButtonRegist;

    private Services service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.getXml();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mButtonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProses();
            }
        });
    }

    private void getXml(){
        this.mNama = findViewById(R.id.name);
        this.mUsername = findViewById(R.id.username);
        this.mEmail = findViewById(R.id.email);
        this.mPhone = findViewById(R.id.phone);
        this.mTglLahir = findViewById(R.id.birthDate);
        this.mAlamat = findViewById(R.id.address);
        this.mCity = findViewById(R.id.city);
        this.mPassword = findViewById(R.id.password);
        this.mRepassword = findViewById(R.id.confirmPass);
        this.mBackButton = findViewById(R.id.backButton);
        this.mButtonRegist = findViewById(R.id.buttonRegis);
    }

    private void registerProses(){

        //check if password not same
        if (!mPassword.getText().toString().matches(mRepassword.getText().toString())){
            Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
            mPassword.setText("");
            mRepassword.setText("");
            return;
        }

        Log.d(TAG, "registerProses: "+
                mUsername.getText().toString()+ " " +
                mEmail.getText().toString()+ " " +
                mNama.getText().toString()+ " " +
                mPassword.getText().toString() + " "+
                mPhone.getText().toString()+ " "+
                mAlamat.getText().toString()+ " "+
                mCity.getText().toString()+" "+
                mRepassword.getText().toString()+" ");

        this.service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> registerCall = service.registerProses(
            this.mUsername.getText().toString(),
            this.mEmail.getText().toString(),
            this.mNama.getText().toString(),
            this.mPassword.getText().toString(),
            this.mPhone.getText().toString(),
            this.mAlamat.getText().toString(),
            Integer.parseInt(this.mCity.getText().toString()),
            this.mRepassword.getText().toString(),
                "ROLPB"
        );

        registerCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(register.this, "Sukses", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: "+response.raw());
                Log.d(TAG, "onResponse: "+response.body());
                Log.d(TAG, "onResponse: "+response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });

    }

}
