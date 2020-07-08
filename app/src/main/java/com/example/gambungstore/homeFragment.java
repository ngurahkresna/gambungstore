package com.example.gambungstore;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.adapters.CategoryAdapter;
import com.example.gambungstore.adapters.ProductAdapter;
import com.example.gambungstore.adapters.PromoAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Profile;
import com.example.gambungstore.models.category.Category;
import com.example.gambungstore.models.category.DataCategory;
import com.example.gambungstore.models.jicash.Jicash;
import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.product.Product;
import com.example.gambungstore.models.promo.DataPromo;
import com.example.gambungstore.models.promo.Promo;
import com.example.gambungstore.progressbar.ProgressBarGambung;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class homeFragment extends Fragment{
    private static final String TAG = "homeFragment";

    private TextView auth;
    private TextView promos;
    private TextView products;
    private TextView categories;
    private TextView jicashInfo;
    private TextView jicashBalance;
    private LinearLayout searchHome;

    private RecyclerView promo;
    private LinearLayoutManager setLayoutManager;
    private PromoAdapter promoAdapter;

    private RecyclerView category;
    private LinearLayoutManager setLayoutManagerCategory;
    private CategoryAdapter categoryAdapter;
    private ConstraintLayout relativeLayout;
    private RecyclerView product;
    private GridLayoutManager setLayoutManagerProduct;
    private ProductAdapter productAdapter;

    private Services service;

    private ProgressBarGambung progressBar;
    private boolean service1 = false, service2 = false, service3 = false, service4 = false;

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
        progressBar = new ProgressBarGambung(getActivity());
        progressBar.startProgressBarGambung();
        Log.d(TAG, "onViewCreated act: "+getActivity());

        if (isLogin()){
            TextView mWelcomeText = view.findViewById(R.id.welcomeText);
            TextView mButtonAuth = view.findViewById(R.id.buttonAuth);

            mWelcomeText.setText(SharedPreference.getRegisteredName(getContext()));
            mButtonAuth.setText(SharedPreference.getRegisteredUsername(getContext()));
        }

        service = Client.getClient(Client.BASE_URL).create(Services.class);

        auth = view.findViewById(R.id.buttonAuth);
        searchHome = view.findViewById(R.id.homeSearch);
        promo = view.findViewById(R.id.promo);
        promos = view.findViewById(R.id.allPromo);
        category = view.findViewById(R.id.category);
        categories = view.findViewById(R.id.allCategory);
        product = view.findViewById(R.id.product);
        products = view.findViewById(R.id.allProduct);
        jicashInfo = view.findViewById(R.id.jicashInfo);
        jicashBalance = view.findViewById(R.id.jicashBalance);
        relativeLayout = view.findViewById(R.id.relativeLayoutJicash);

        if (isLogin()) {
            getJicash();
        }else{
            jicashBalance.setText("Login Untuk Melihat Jicash");
            service4 = true;
            if(service1 && service2 && service3 && service4){
                progressBar.endProgressBarGambung();
            }
        }
        getCategory();
        getProduct();
        getPromo();

        auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin())
                    ((homeActivity) getActivity()).removeBottomNavigation();
                else
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
                startActivity(new Intent(getContext(), SearchActivity.class));
//                getActivity().onBackPressed();
            }
        });

        jicashInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    startActivity(new Intent(getContext(), jiCashHomeActivity.class));
                }
                else{
                    Toast.makeText(getActivity(), "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    startActivity(new Intent(getContext(), jiCashHomeActivity.class));
                }
                else{
                    Toast.makeText(getActivity(), "Silahkan login terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isLogin(){
        if (SharedPreference.getRegisteredToken(getContext()).matches("")){
            return false;
        }else{
            return true;
        }
    }

    public void onViewPromo(List<DataPromo> dataPromos) {
        promo.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        promo.setLayoutManager(setLayoutManager);

        // specify an adapter (see also next example)
        promoAdapter = new PromoAdapter(dataPromos, getContext());
        promo.setAdapter(promoAdapter);
        service1 = true;
        if(service1 && service2 && service3 && service4){
            progressBar.endProgressBarGambung();
        }
    }

    public void onViewCategory(List<DataCategory> categories) {
        category.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        category.setLayoutManager(setLayoutManager);

        // specify an adapter (see also next example)
        categoryAdapter = new CategoryAdapter(categories, getContext(),"HomeFragment");
        category.setAdapter(categoryAdapter);
        service2 = true;
        if(service1 && service2 && service3 && service4){
            progressBar.endProgressBarGambung();
        }
    }

    public void onViewProduct(List<DataProduct> dataProducts) {
        product.setHasFixedSize(true);

        // use a linear layout manager
        setLayoutManagerProduct = new GridLayoutManager(getContext(), 3);
        product.setLayoutManager(setLayoutManagerProduct);

        // specify an adapter (see also next example)
        productAdapter = new ProductAdapter(dataProducts, getContext());
        product.setAdapter(productAdapter);

        service3 = true;
        if(service1 && service2 && service3 && service4){
            progressBar.endProgressBarGambung();
        }
    }

    private void getCategory(){
        Call<Category> callCategory = service.getCategory();
        callCategory.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                List<DataCategory> dataCategories = response.body().getCategories();
                onViewCategory(dataCategories);
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                Toast.makeText(getContext(), "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressBar.endProgressBarGambung();
            }
        });
    }

    private void getProduct(){
        Call<Product> callProduct = service.getProduct();
        callProduct.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                List<DataProduct> dataProducts = response.body().getProducts();
                onViewProduct(dataProducts);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getContext(), "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressBar.endProgressBarGambung();
            }
        });
    }

    private void getPromo(){
        Call<Promo> callPromo = service.getPromo();
        callPromo.enqueue(new Callback<Promo>() {
            @Override
            public void onResponse(Call<Promo> call, Response<Promo> response) {
                List<DataPromo> dataPromos = response.body().getDataPromo();
                onViewPromo(dataPromos);
            }

            @Override
            public void onFailure(Call<Promo> call, Throwable t) {
                Toast.makeText(getContext(), "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressBar.endProgressBarGambung();
            }
        });
    }

    private void getJicash(){
        Call<List<Jicash>> callJicash = service.getJicash(
                SharedPreference.getRegisteredUsername(getContext())
        );
        callJicash.enqueue(new Callback<List<Jicash>>() {
            @Override
            public void onResponse(Call<List<Jicash>> call, Response<List<Jicash>> response) {
                if (response.body().size() != 0) {
                    jicashBalance.setText("Rp. " + response.body().get(0).getBalance().toString() + ",-");
                }
                else {
                    jicashBalance.setText("Rp. 0");
                }
                service4 = true;
                if(service1 && service2 && service3 && service4){
                    progressBar.endProgressBarGambung();
                }
                Log.d(TAG, "onResponse: "+response.raw());
            }

            @Override
            public void onFailure(Call<List<Jicash>> call, Throwable t) {
                Toast.makeText(getContext(), "terjadi kesalahan, silahkan coba lagi", Toast.LENGTH_SHORT).show();
                progressBar.endProgressBarGambung();
            }
        });
    }
}
