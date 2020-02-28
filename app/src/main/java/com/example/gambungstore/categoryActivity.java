package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

    private List<DataCategory> dataCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mCategoryRecyclerView = findViewById(R.id.category);

        buttonBack = findViewById(R.id.backButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(categoryActivity.this, homeFragment.class));
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
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }
}
