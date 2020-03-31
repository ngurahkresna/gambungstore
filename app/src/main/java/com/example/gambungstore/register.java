package com.example.gambungstore;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Profile;
import com.example.gambungstore.models.RajaOngkir;
import com.example.gambungstore.models.ResultCities;
import com.example.gambungstore.services.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register extends AppCompatActivity implements DatePickerFragment.TheListener {
    private static final String TAG = "registerActivity";

    private EditText mNama,mUsername,mEmail,mPhone,mTglLahir,mAlamat,mPassword,mRepassword,mCity;
    private TextView mBackButton;
    private Button mButtonRegist;
    private String mCityId;

    private Services service;

    private ArrayList<String> SpinnerNameCities = new ArrayList<>();
    private ArrayList<Integer> SpinnerIdCities = new ArrayList<>();
    private ArrayList<String> usernameExist = new ArrayList<>();
    private ArrayList<String> emailExist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.getXml();
        this.callCitiesApi();
        this.callUsersApi();

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

    private void registerProses() {
        if (checkField()) {
            //check if password not same
            if (!mPassword.getText().toString().matches(mRepassword.getText().toString())) {
                Toast.makeText(this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
                mPassword.setText("");
                mRepassword.setText("");
                return;
            }

            this.service = Client.getClient(Client.BASE_URL).create(Services.class);
            Call<ResponseBody> registerCall = service.registerProses(
                    this.mUsername.getText().toString(),
                    this.mEmail.getText().toString(),
                    this.mNama.getText().toString(),
                    this.mPassword.getText().toString(),
                    this.mPhone.getText().toString(),
                    this.mAlamat.getText().toString(),
                    this.mCityId,
                    this.mRepassword.getText().toString(),
                    "ROLPB",
                    this.mTglLahir.getText().toString()
            );

            registerCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Intent intent = new Intent(register.this, login.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.toString());
                }
            });
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
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

    private boolean checkField() {
        if (String.valueOf(mNama.getText()).matches("") ||
                String.valueOf(mUsername.getText()).matches("") ||
                String.valueOf(mEmail.getText()).matches("") ||
                String.valueOf(mPassword.getText()).matches("") ||
                String.valueOf(mRepassword.getText()).matches("") ||
                String.valueOf(mPhone.getText()).matches("") ||
                String.valueOf(mAlamat.getText()).matches("") ||
                String.valueOf(mCity.getText()).matches("")) {

            Toast.makeText(register.this, "Lengkapi Data Anda", Toast.LENGTH_SHORT).show();
            return false;
        }

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(mEmail.getText());
        if (!matcher.matches()) {
            Toast.makeText(register.this, "Format Email Salah", Toast.LENGTH_SHORT).show();
            mEmail.requestFocus();
            return false;
        }

        if (usernameExist.contains(mUsername.getText().toString())) {
            Toast.makeText(register.this, "Username Sudah Ada", Toast.LENGTH_SHORT).show();
            mUsername.requestFocus();
            return false;
        }

        if (emailExist.contains(mEmail.getText().toString())) {
            Toast.makeText(register.this, "Email Sudah Terdaftar", Toast.LENGTH_SHORT).show();
            mEmail.requestFocus();
            return false;
        }

        return true;
    }

    private void callCitiesApi() {
        this.service = Client.getRajaongkir().create(Services.class);
        Call<RajaOngkir> dataCities = service.getRajaongkir();
        dataCities.enqueue(new Callback<RajaOngkir>() {
            @Override
            public void onResponse(Call<RajaOngkir> call, Response<RajaOngkir> response) {
                RajaOngkir RajaOngkirCities = response.body();
                for(ResultCities rs : RajaOngkirCities.getCities().getResult()){
                    SpinnerNameCities.add(rs.getCityName());
                    SpinnerIdCities.add(rs.getCityId());
                }
            }

            @Override
            public void onFailure(Call<RajaOngkir> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
            }
        });
    }

    private void callUsersApi() {
        this.service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<Profile>> users = service.getUsers();
        users.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                for(Profile rs : response.body()){
                    usernameExist.add(rs.getUsername());
                    emailExist.add(rs.getEmail());
                }
            }

            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}
