package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Profile;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editProfile extends AppCompatActivity {

    private static final String TAG = "editProfile";

    private EditText mNama,mUsername,mEmail,mPhone,mTglLahir,mAlamat,mPassword,mRepassword,mCity;
    private Button mButtonSubmit;
    private ImageView mBackArrow;

    private Services service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        this.getXml();
        this.getProfile();

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProfile();
            }
        });

    }

    private void getProfile(){
        service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Profile> profileCall = service.getProfile(
                "Bearer "+ SharedPreference.getRegisteredToken(this)
        );
        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                Log.d(TAG, "onResponse: "+response.body().getUsername());
                mNama.setText(response.body().getName());
                mUsername.setText(response.body().getUsername());
                mEmail.setText(response.body().getEmail());
                mPhone.setText(response.body().getPhone());
                mTglLahir.setText(response.body().getBirthday());
                mAlamat.setText(response.body().getAddress());
                mCity.setText(Integer.toString(response.body().getCity()));

                //disabled
                mUsername.setKeyListener(null);
                mEmail.setKeyListener(null);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
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
        this.mButtonSubmit = findViewById(R.id.buttonSubmit);
        this.mBackArrow = findViewById(R.id.backArrow);
    }

    private void submitProfile(){

    }
}
