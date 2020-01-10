package com.example.gambungstore;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment {

    private TextView auth;
    private TextView promos;
    private LinearLayout searchHome;

    private RecyclerView promo;
    private LinearLayoutManager setLayoutManager;
    private adapterPromo promoAdapter;

    private RecyclerView category;
    private LinearLayoutManager setLayoutManagerCategory;
    private adapterCategory categoryAdapter;

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

        auth = view.findViewById(R.id.buttonAuth);
        searchHome = view.findViewById(R.id.homeSearch);
        promo = view.findViewById(R.id.promo);
        promos = view.findViewById(R.id.allPromo);
        category = view.findViewById(R.id.category);
        product = view.findViewById(R.id.product);
        onViewPromo();
        onViewCategory();
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

        searchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getContext(), SplashScreenActivity.class));
                getActivity().onBackPressed();
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

    public void onViewCategory() {
        category.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        category.setLayoutManager(setLayoutManager);

        // specify an adapter (see also next example)
        categoryAdapter = new adapterCategory();
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


}
