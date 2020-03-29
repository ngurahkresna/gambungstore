package com.example.gambungstore;


import android.app.ProgressDialog;
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
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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

    private List<DataCart> listCart;

    private Button mCheckout;

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

        mCheckout = getView().findViewById(R.id.checkoutButton);
        mCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout();
                Intent intent = new Intent(getActivity(), CheckoutForm.class);
                startActivity(intent);
            }
        });

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
                    listCart = response.body().getDataCart();
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

    public void checkout(){
        progressbar.startProgressBarGambung();

        ArrayList<Integer> cart_id = new ArrayList<Integer>();
        ArrayList<Integer> quantity = new ArrayList<Integer>();
        for (int i = 0; i < listCart.size(); i++) {
            Log.d(TAG, "checkout: "+i);
            cart_id.add(listCart.get(i).getId());
            quantity.add(listCart.get(i).getQuantity());
        }

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<ResponseBody> callCheckout = service.checkout(
          cart_id,
          quantity,
          SharedPreference.getRegisteredUsername(getContext())
        );

        callCheckout.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: "+response.body());
                progressbar.endProgressBarGambung();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });

    }
}
