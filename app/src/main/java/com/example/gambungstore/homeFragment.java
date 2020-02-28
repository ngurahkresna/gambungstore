package com.example.gambungstore;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gambungstore.adapters.CategoryAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Category.Category;
import com.example.gambungstore.models.Category.DataCategory;
import com.example.gambungstore.services.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {
    private static final String TAG = "homeFragment";

    private TextView auth;
    private TextView promos;
    private TextView products;
    private TextView categories;
    private LinearLayout searchHome;

    private RecyclerView promo;
    private LinearLayoutManager setLayoutManager;
    private adapterPromo promoAdapter;

    private RecyclerView category;
    private LinearLayoutManager setLayoutManagerCategory;
    private CategoryAdapter categoryAdapter;

    private RecyclerView product;
    private GridLayoutManager setLayoutManagerProduct;
    private adapterProduct productAdapter;

    public homeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCategory();

        auth = view.findViewById(R.id.buttonAuth);
        searchHome = view.findViewById(R.id.homeSearch);
        promo = view.findViewById(R.id.promo);
        promos = view.findViewById(R.id.allPromo);
        category = view.findViewById(R.id.category);
        categories = view.findViewById(R.id.allCategory);
        product = view.findViewById(R.id.product);
        products = view.findViewById(R.id.allProduct);
        onViewPromo();
        onViewProduct();


        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), authOption.class));
            }
        });

        promos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), promoActivity.class));
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), categoryActivity.class));
            }
        });

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), productActivity.class));
            }
        });

        searchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), searchActivity.class));
//                getActivity().onBackPressed();
            }
        });
    }

    public void onViewPromo() {
        promo.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        promo.setLayoutManager(setLayoutManager);

        // specify an adapter (see also next example)
        promoAdapter = new adapterPromo();
        promo.setAdapter(promoAdapter);
    }

    public void onViewCategory(List<DataCategory> categories) {
        category.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        category.setLayoutManager(setLayoutManager);

        // specify an adapter (see also next example)
        categoryAdapter = new CategoryAdapter(categories, getContext(),"HomeFragment");
        category.setAdapter(categoryAdapter);
    }

    public void onViewProduct() {
        product.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManagerProduct = new GridLayoutManager(getContext(), 3);
        product.setLayoutManager(setLayoutManagerProduct);

        // specify an adapter (see also next example)
        productAdapter = new adapterProduct();
        product.setAdapter(productAdapter);
    }

    private void getCategory(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Category> callCategory = service.getCategory();
        callCategory.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                List<DataCategory> dataCategories = response.body().getCategories();
                onViewCategory(dataCategories);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }

}
