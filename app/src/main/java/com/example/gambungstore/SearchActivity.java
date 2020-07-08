package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.services.Services;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    
    private ImageView buttonBack;
    private EditText mSearchHint;
    private LinearLayout btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buttonBack = findViewById(R.id.backButton);
        mSearchHint = findViewById(R.id.searchHint);

        btnSearch = findViewById(R.id.searchButton);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProduct(mSearchHint.getText().toString());
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mSearchHint.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    searchProduct(mSearchHint.getText().toString());
                }
                return false;
            }
        });
    }

    public void searchProduct(String key){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<DataProduct>> callSearch = service.searchProduct(key);
        callSearch.enqueue(new Callback<List<DataProduct>>() {
            @Override
            public void onResponse(Call<List<DataProduct>> call, Response<List<DataProduct>> response) {
                List<DataProduct> dataProducts = response.body();
                if (dataProducts.isEmpty()){
                    Toast.makeText(SearchActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SearchActivity.this, productActivity.class);
                    intent.putExtra("status", "search");
                    intent.putParcelableArrayListExtra("dataproduct", (ArrayList<? extends Parcelable>) dataProducts);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(SearchActivity.this, productActivity.class);
                intent.putExtra("status", "search");
                intent.putParcelableArrayListExtra("dataproduct", (ArrayList<? extends Parcelable>) dataProducts);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<DataProduct>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
