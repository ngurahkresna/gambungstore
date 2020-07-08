package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.adapters.CategoryAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.category.Category;
import com.example.gambungstore.models.category.DataCategory;
import com.example.gambungstore.services.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class categoryActivity extends AppCompatActivity {

    private static final String TAG = "categoryActivity";
    
    private ImageView buttonBack;
    private RecyclerView mCategoryRecyclerView;
    private LinearLayoutManager setLayoutManagerCategory;
    private CategoryAdapter categoryAdapter;
    private EditText searchHint;
    private LinearLayout btnSearch;

    private List<DataCategory> dataCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mCategoryRecyclerView = findViewById(R.id.category);
        searchHint = findViewById(R.id.searchHint);
        btnSearch = findViewById(R.id.searchButton);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCategory(searchHint.getText().toString());
            }
        });

        searchHint.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {;
                searchCategory(searchHint.getText().toString());
                return true;
            }
        });

        buttonBack = findViewById(R.id.backButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getCategory();
    }

    public void onViewCategory(List<DataCategory> dataCategories) {
        mCategoryRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManagerCategory = new LinearLayoutManager(categoryActivity.this);
        mCategoryRecyclerView.setLayoutManager(setLayoutManagerCategory);

        // specify an adapter (see also next example)
        categoryAdapter = new CategoryAdapter(dataCategories, this,"CategoryActivity");
        mCategoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void getCategory(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Category> callCategory = service.getCategory();
        callCategory.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                dataCategories = response.body().getCategories();
                Log.d(TAG, "onResponse: "+dataCategories.get(0).getName());
                onViewCategory(dataCategories);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(categoryActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchCategory(String key){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<DataCategory>> callSearch = service.searchCategory(key);
        callSearch.enqueue(new Callback<List<DataCategory>>() {
            @Override
            public void onResponse(Call<List<DataCategory>> call, Response<List<DataCategory>> response) {
                List<DataCategory> dataCategories = response.body();
                if (dataCategories.isEmpty()){
                    Toast.makeText(categoryActivity.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    onViewCategory(dataCategories);
                    return;
                }

                onViewCategory(dataCategories);
            }

            @Override
            public void onFailure(Call<List<DataCategory>> call, Throwable t) {
                Toast.makeText(categoryActivity.this, "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
