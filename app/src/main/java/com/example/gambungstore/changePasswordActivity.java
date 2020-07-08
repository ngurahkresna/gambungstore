package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

public class changePasswordActivity extends AppCompatActivity {

    private static final String TAG = "changePasswordActivity";

    private EditText mPassword;
    private EditText mNewPassword;
    private EditText mConfirmPassword;

    private ImageView mbackBtn;

    private Services service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mPassword = findViewById(R.id.password);
        mNewPassword = findViewById(R.id.newPassword);
        mConfirmPassword = findViewById(R.id.confirmPassword);

        mbackBtn = findViewById(R.id.backBtnChangePass);
        mbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(changePasswordActivity.this,homeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void changePass(View view) {
        String pass = this.mPassword.getText().toString();
        String newPass = this.mNewPassword.getText().toString();
        String confirmPass = this.mConfirmPassword.getText().toString();

        Log.d("pass", String.valueOf(pass));
        Log.d("newPass", String.valueOf(newPass));
        Log.d("confirmPass", String.valueOf(confirmPass));

        if (!newPass.equals(confirmPass)) {
            Toast.makeText(changePasswordActivity.this, "Konfirmasi password tidak sesuai", Toast.LENGTH_SHORT).show();
        } else {
            service = Client.getClient(Client.BASE_URL).create(Services.class);
            Call<ResponseBody> checkOldPass = service.checkOldPass(
                    "Bearer "+ SharedPreference.getRegisteredToken(this),
                    pass
            );
            checkOldPass.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d("code", String.valueOf(response));
                    if (response.code() == 200) {
                        updatePass();
                    } else {
                        Toast.makeText(changePasswordActivity.this, "Password lama tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(changePasswordActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void updatePass() {
        service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> setPass = service.updatePass(
                "Bearer "+ SharedPreference.getRegisteredToken(this),
                this.mNewPassword.getText().toString(),
                this.mConfirmPassword.getText().toString()
        );
        setPass.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                finish();
                Intent intent = new Intent(changePasswordActivity.this,homeActivity.class);
                startActivity(intent);
                Toast.makeText(changePasswordActivity.this, "Berhasil Diubah", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(changePasswordActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
