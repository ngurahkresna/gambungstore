package com.example.gambungstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.adapters.ExpeditionCheckoutAdapter;
import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.checkout.Checkout;
import com.example.gambungstore.models.store.DataStore;
import com.example.gambungstore.services.Services;
import com.example.gambungstore.sharedpreference.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CheckoutForm extends AppCompatActivity {
    private static final String TAG = "CheckoutForm";
    
    RecyclerView mCheckoutCard;

    TextView mPayment;
    TextView mAddress, mProductPrice;
    EditText mPhone;
    EditText mPromo;

    ArrayList<String> expedition = new ArrayList<>();
    ArrayList<String> paymentMethod = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_form);

        mCheckoutCard = findViewById(R.id.checkoutCard);
        mPayment = findViewById(R.id.payment);
        mAddress = findViewById(R.id.addressValue);
        mPhone = findViewById(R.id.phoneValue);
        mPromo = findViewById(R.id.promoValue);
        mProductPrice = findViewById(R.id.productPrice);

        getData();

        expedition.add("JNE");
        expedition.add("TIKI");
        expedition.add("POS INDONESIA");

        paymentMethod.add("Ji-Cash");
        paymentMethod.add("Transfer");
    }

    public void paymentMethod(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Metode Pembayaran");

        builder.setItems(paymentMethod.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CheckoutForm.this, "You click " + paymentMethod.get(which), Toast.LENGTH_SHORT).show();
                mPayment.setText(paymentMethod.get(which));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkoutAdapter(List<DataStore> listStores) {
        ExpeditionCheckoutAdapter checkoutAdapter = new ExpeditionCheckoutAdapter(listStores,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mCheckoutCard.setLayoutManager(linearLayoutManager);
        mCheckoutCard.setAdapter(checkoutAdapter);
    }

    public void submitButton(View view) {
        Toast.makeText(CheckoutForm.this, "Go to next step", Toast.LENGTH_SHORT).show();
    }

    public void backToHome(View view) {
        onBackPressed();
    }

    public void getData(){
        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<Checkout> callCheckout = service.getCheckout(
                SharedPreference.getRegisteredUsername(this)
        );

        callCheckout.enqueue(new Callback<Checkout>() {
            @Override
            public void onResponse(Call<Checkout> call, Response<Checkout> response) {
                Log.d(TAG, "onResponse: "+response.raw());
                mAddress.setText(response.body().getUser().getAddress().toString());
                mPhone.setText(response.body().getUser().getPhone().toString());
                mProductPrice.setText("Rp "+Integer.toString(response.body().getPrice())+",-");
                checkoutAdapter(response.body().getStore());
            }

            @Override
            public void onFailure(Call<Checkout> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.toString());
            }
        });
    }
}
