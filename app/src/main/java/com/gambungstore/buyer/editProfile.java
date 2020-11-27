package com.gambungstore.buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gambungstore.buyer.client.Client;
import com.gambungstore.buyer.models.Profile;
import com.gambungstore.buyer.models.RajaOngkir;
import com.gambungstore.buyer.models.ResultCities;
import com.gambungstore.buyer.progressbar.ProgressBarGambung;
import com.gambungstore.buyer.services.Services;
import com.gambungstore.buyer.sharedpreference.SharedPreference;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editProfile extends AppCompatActivity implements DatePickerFragment.TheListener {

    private static final String TAG = "editProfile";

    private EditText mNama,mUsername,mEmail,mPhone,mTglLahir,mAlamat,mPassword,mRepassword,mCity;
    private Button mButtonSubmit;
    private ImageView mBackArrow;
    private String mCityId;

    private Services service;

    private ArrayList<String> SpinnerNameCities = new ArrayList<>();
    private ArrayList<Integer> SpinnerIdCities = new ArrayList<>();

    private ProgressBarGambung progressBarGambung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        progressBarGambung = new ProgressBarGambung(this);
        progressBarGambung.startProgressBarGambung();

        this.getXml();
        this.getProfile();
        this.callCitiesApi();

        mBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(editProfile.this,homeActivity.class);
                startActivity(intent);
            }
        });
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitProfile();
            }
        });

        progressBarGambung.endProgressBarGambung();
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
                mCityId = response.body().getCity();
//                mCity.setText(response.body().getCity());

                //disabled
                mUsername.setKeyListener(null);
                mEmail.setKeyListener(null);
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(editProfile.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressBarGambung.endProgressBarGambung();
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
        service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> updateProfilCall = service.updateProfile(
                SharedPreference.getRegisteredId(this),
                this.mNama.getText().toString(),
                this.mPhone.getText().toString(),
                this.mTglLahir.getText().toString(),
                this.mAlamat.getText().toString(),
                Integer.parseInt(this.mCityId)
        );
        updateProfilCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: "+response.raw());
                Toast.makeText(editProfile.this, "Berhasil Diubah", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(editProfile.this,homeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(editProfile.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressBarGambung.endProgressBarGambung();
            }
        });
    }

    public void showDatePicker(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dateAsText", mTglLahir.getText().toString());
        datePicker.setArguments(bundle);
        datePicker.show(getSupportFragmentManager(),"datePicker");
    }

    @Override
    public void returnDate(String date) {
        mTglLahir.setText(date);
    }

    public void showCity(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(editProfile.this);
        builder.setTitle("Pilih Kota");

        builder.setItems(SpinnerNameCities.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCity.setText(SpinnerNameCities.get(which));
                mCityId = String.valueOf(SpinnerIdCities.get(which));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void callCitiesApi() {
        this.service = Client.getRajaongkir().create(Services.class);
        Call<RajaOngkir> dataCities = service.getRajaongkir();
        dataCities.enqueue(new Callback<RajaOngkir>() {
            @Override
            public void onResponse(Call<RajaOngkir> call, Response<RajaOngkir> response) {
                RajaOngkir RajaOngkirCities = response.body();
                for(ResultCities rs : RajaOngkirCities.getCities().getResult()){
                    if (mCityId.equals(Integer.toString(rs.getCityId()))){
                        mCity.setText(rs.getCityName());
                    }
                    SpinnerNameCities.add(rs.getCityName());
                    SpinnerIdCities.add(rs.getCityId());
                }
            }

            @Override
            public void onFailure(Call<RajaOngkir> call, Throwable t) {
                Toast.makeText(editProfile.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressBarGambung.endProgressBarGambung();
            }
        });
    }
}
