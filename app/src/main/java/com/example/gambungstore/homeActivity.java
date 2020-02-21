package com.example.gambungstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Profile;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "homeActivity";

    private Services service;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loadFragment(new homeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_menu);

        Log.d(TAG, "onCreate: apakah login ? "+isLogin());

        if (isLogin()){
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
                fragment = new homeFragment();
                break;

            case R.id.chat_menu:
                fragment = new homeFragment();
                break;

        }
        return loadFragment(fragment);
    }

    private boolean isLogin(){
        if (SharedPreference.getRegisteredToken(this).matches("")){
            return false;
        }else{
            return true;
        }
    }

    private void getProfile(){
        service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Profile> profileCall = service.getProfile(
                "Bearer "+SharedPreference.getRegisteredToken(this)
        );
        profileCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                SharedPreference.setRegisteredId(getBaseContext(),response.body().getId());
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }
    
}

