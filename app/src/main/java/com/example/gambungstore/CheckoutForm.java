package com.example.gambungstore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gambungstore.adapters.ExpeditionCheckoutAdapter;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class CheckoutForm extends AppCompatActivity {

    RecyclerView mCheckoutCard;

    TextView mExpeditionChoosen;
    TextView mPayment;

    ArrayList<String> expedition = new ArrayList<>();
    ArrayList<String> paymentMethod = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_form);

        mCheckoutCard = findViewById(R.id.checkoutCard);
        mPayment = findViewById(R.id.payment);

        View inflatedView = getLayoutInflater().inflate(R.layout.expedition_card_checkout, null);
        mExpeditionChoosen = (TextView) inflatedView.findViewById(R.id.expeditionChoosed);
        mExpeditionChoosen.setText("Hello!");

        expedition.add("JNE");
        expedition.add("TIKI");
        expedition.add("POS INDONESIA");

        paymentMethod.add("Ji-Cash");
        paymentMethod.add("Transfer");

        checkoutAdapter();
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

    public void chooseExpedition(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Ekspedisi");

        builder.setItems(expedition.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CheckoutForm.this, "You click " + expedition.get(which), Toast.LENGTH_SHORT).show();
                mExpeditionChoosen.setText(expedition.get(which));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkoutAdapter() {
        ExpeditionCheckoutAdapter checkoutAdapter = new ExpeditionCheckoutAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mCheckoutCard.setLayoutManager(linearLayoutManager);
        mCheckoutCard.setAdapter(checkoutAdapter);
    }

    public void submitButton(View view) {
        Toast.makeText(CheckoutForm.this, "Go to next step", Toast.LENGTH_SHORT).show();
    }

    public void backToHome(View view) {

    }
}
