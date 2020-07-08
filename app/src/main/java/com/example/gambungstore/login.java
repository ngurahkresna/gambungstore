package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Login;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    private static final String TAG = "loginActivity";
    
    private EditText mEmail,mPassword;
    private  TextView mBackButton;
    private Button mLoginButton;
    
    Services service;

    ProgressBarGambung progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getXml();
        progressbar = new ProgressBarGambung(this);
        
        this.mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        
        this.mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.startProgressBarGambung();
                loginProcess();
                progressbar.endProgressBarGambung();
            }
        });
    }

    private void getXml(){
        this.mEmail = findViewById(R.id.email);
        this.mPassword = findViewById(R.id.password);
        this.mBackButton = findViewById(R.id.backButton);
        this.mLoginButton = findViewById(R.id.loginButton);
    }
    
    private void loginProcess(){
        if (mEmail.getText().toString().isEmpty()){
            Toast.makeText(this, "Username Belum Diisi", Toast.LENGTH_SHORT).show();
            progressbar.endProgressBarGambung();
            return;
        }
        
        if (mPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "Password Belum Diisi", Toast.LENGTH_SHORT).show();
            progressbar.endProgressBarGambung();
            return;
        }

        this.service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Login> loginCall = service.loginProcess(
          mEmail.getText().toString(),
          mPassword.getText().toString(),
          "buyer"      
        );
        
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Log.d(TAG, "onResponse: "+response.raw());
                if (response.code() == 422){
                    Toast.makeText(login.this, "Username/Password Anda Salah", Toast.LENGTH_SHORT).show();
                }else if(response.body().getVerified() == null){
                    Toast.makeText(login.this, "Anda belum verifikasi email, silahkan verifikasi terlebih dahulu!", Toast.LENGTH_SHORT).show();
                } else{
                    Log.d(TAG, "onResponse: "+response.body().getToken());
                    SharedPreference.setRegisteredToken(getApplicationContext(),response.body().getToken());
                    Intent intent = new Intent(login.this, homeActivity.class);// New activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    progressbar.endProgressBarGambung();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(login.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressbar.endProgressBarGambung();
            }
        });
        
    }
}
