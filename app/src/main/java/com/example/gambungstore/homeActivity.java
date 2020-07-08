package com.example.gambungstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Profile;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

public class homeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "homeActivity";
    private long backPressedTime;
    private Services service;

    protected ProgressBarGambung progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadFragment(new homeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_menu);

        Log.d(TAG, "onCreate: apakah login ? " + isLogin());

        //move to fragment
        if (getIntent() != null) {
            String fragmentMoveTo = getIntent().getStringExtra("fragment");
            if (fragmentMoveTo != null) {
                if (fragmentMoveTo.equals("cart")) {
                    bottomNavigationView.setSelectedItemId(R.id.cart_menu);
                    Fragment fragment = new cartFragment();
                    loadFragment(fragment);
                    return;
                }else if(fragmentMoveTo.equals("transaction")){
                    bottomNavigationView.setSelectedItemId(R.id.transaction_menu);
                    Fragment fragment = new transaction();
                    loadFragment(fragment);
                    return;
                } else if(fragmentMoveTo.equals("chat")) {
                    Fragment fragment = new chatFragment();
                    loadFragment(fragment);
                    return;
                }
            }
        }

        if (isLogin()) {
            getProfile();
        }


    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home, fragment).commit();
            return true;
        }
        return false;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home_menu:
                fragment = new homeFragment();
                break;

            case R.id.cart_menu:
                fragment = new cartFragment();
                break;

            case R.id.transaction_menu:
                fragment = new transaction();
                break;

            case R.id.chat_menu:
                fragment = new chatFragment();
                break;

        }
        return loadFragment(fragment);
    }

    private boolean isLogin() {
        if (SharedPreference.getRegisteredToken(this).matches("")) {
            return false;
        } else {
            return true;
        }
    }

    private void getProfile() {
        service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Profile> profileCall = service.getProfile(
                "Bearer " + SharedPreference.getRegisteredToken(this)
        );
        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                SharedPreference.setRegisteredId(getBaseContext(), response.body().getId());
                SharedPreference.setRegisteredUsername(getBaseContext(), response.body().getUsername());
                SharedPreference.setRegisteredName(getBaseContext(), response.body().getName());

                changeLoginLayout(response.body().getUsername(), response.body().getName());
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(homeActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changeLoginLayout(String username, String name) {
        TextView mWelcomeText = findViewById(R.id.welcomeText);
        TextView mButtonAuth = findViewById(R.id.buttonAuth);

        mWelcomeText.setText(name);
        mButtonAuth.setText(username);
        mButtonAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeBottomNavigation();
            }
        });

    }

    public void refreshMenu() {
        findViewById(R.id.fragmentHome).setVisibility(View.VISIBLE);
        findViewById(R.id.bottomNavigation).setVisibility(View.VISIBLE);
        TextView mWelcomeText = findViewById(R.id.welcomeText);
        TextView mButtonAuth = findViewById(R.id.buttonAuth);

        mWelcomeText.setText("Guest");
        mButtonAuth.setText("Login/Register");
    }

    public void removeBottomNavigation()
    {
        if (SharedPreference.getRegisteredId(this) != 0){
            sideBar fragment = new sideBar();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.homeFragment,fragment);
            transaction.commit();
            findViewById(R.id.fragmentHome).setVisibility(View.GONE);
            findViewById(R.id.bottomNavigation).setVisibility(View.GONE);
        }

    }
    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
            System.exit(0);
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tekan kembali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();

    }

}

