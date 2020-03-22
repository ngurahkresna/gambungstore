package com.example.gambungstore;


import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gambungstore.adapters.CartAdapter;
import com.example.gambungstore.adapters.ProductAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.cart.Cart;
import com.example.gambungstore.models.cart.DataCart;
import com.example.gambungstore.models.product.DataProduct;
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
public class cartFragment extends Fragment {
    private static final String TAG = "cartFragment";

    private RecyclerView cart;
    private LinearLayoutManager setLayoutManagerCart;
    private CartAdapter cartAdapter;

    private ProgressBarGambung progressbar;

    public cartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressbar = new ProgressBarGambung(getActivity());
        progressbar.startProgressBarGambung();
        if (!SharedPreference.getRegisteredToken(getContext()).matches("")) {
            getCartData();
        } else {
            progressbar.endProgressBarGambung();
        }
    }

    private void getCartData(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Cart> callCart = service.getCart(
                SharedPreference.getRegisteredUsername(getContext())
        );
        callCart.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (!response.body().getDataCart().isEmpty()) {
                    getView().findViewById(R.id.cartEmpty).setVisibility(View.GONE);
                    getView().findViewById(R.id.cartEmptyText).setVisibility(View.GONE);
                    getView().findViewById(R.id.cartRecycleView).setVisibility(View.VISIBLE);
                    viewRecyclerCart(response.body().getDataCart());
                    TextView mTotal = getView().findViewById(R.id.cartTotal);
                    mTotal.setText("Rp "+getTotalHarga(response.body().getDataCart())+",-");
                }
                progressbar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
                progressbar.endProgressBarGambung();
            }
        });
    }

    public void viewRecyclerCart(List<DataCart> dataCarts) {
        cart = getView().findViewById(R.id.cartRecycleView);

        // use a linear layout manager
        setLayoutManagerCart = new LinearLayoutManager(getContext());
        cart.setLayoutManager(setLayoutManagerCart);

        // specify an adapter (see also next example)
        cartAdapter = new CartAdapter(dataCarts,getContext());
        cart.setAdapter(cartAdapter);
    }

    private String getTotalHarga(List<DataCart> dataCarts){
        int harga = 0;
        for (int i = 0; i < dataCarts.size(); i++){
            harga += Integer.valueOf(dataCarts.get(i).getPrice());
        }
        return Integer.toString(harga);
    }


}
