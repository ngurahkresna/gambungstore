package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class categoryActivity extends AppCompatActivity {

    private ImageView buttonBack;
    private RecyclerView category;
    private LinearLayoutManager setLayoutManagerCategory;
    private adapterCategoryWidth categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        category = findViewById(R.id.category);

        buttonBack = findViewById(R.id.backButton);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(categoryActivity.this, homeFragment.class));
            }
        });

        onViewCategory();
    }

    public void onViewCategory() {
        category.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManagerCategory = new LinearLayoutManager(categoryActivity.this);
        category.setLayoutManager(setLayoutManagerCategory);

        // specify an adapter (see also next example)
        categoryAdapter = new adapterCategoryWidth();
        category.setAdapter(categoryAdapter);
    }
}
