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
                SharedPreference.setRegisteredUsername(getBaseContext(),response.body().getUsername());
                SharedPreference.setRegisteredName(getBaseContext(),response.body().getName());

                changeLoginLayout(response.body().getUsername(), response.body().getName());
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

    public void changeLoginLayout(String username, String name){
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

    public void refreshMenu(){
        findViewById(R.id.fragmentHome).setVisibility(View.VISIBLE);
        findViewById(R.id.bottomNavigation).setVisibility(View.VISIBLE);
        TextView mWelcomeText = findViewById(R.id.welcomeText);
        TextView mButtonAuth = findViewById(R.id.buttonAuth);

        mWelcomeText.setText("Guest");
        mButtonAuth.setText("Login/Register");
    }

    public void removeBottomNavigation()
    {
        sideBar fragment = new sideBar();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.homeFragment,fragment);
        transaction.commit();
        findViewById(R.id.fragmentHome).setVisibility(View.GONE);
        findViewById(R.id.bottomNavigation).setVisibility(View.GONE);
    }

}

