package com.gambungstore.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CheckoutDone extends AppCompatActivity {
    private static final String TAG = "CheckoutDone";

    Button mSubmitButtom,mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_done);

        mSubmitButtom = findViewById(R.id.submitButton);
        mBackButton = findViewById(R.id.backButton);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CheckoutDone.this,homeActivity.class);
                startActivity(intent);
            }
        });

        mSubmitButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+getIntent().getStringExtra("payment"));
                if (getIntent() != null) {
                    if (getIntent().getStringExtra("payment").equals("jicash")) {
                        Intent intent = new Intent(CheckoutDone.this, jiCashHomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(CheckoutDone.this, homeActivity.class);
                        intent.putExtra("fragment", "transaction");
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });
    }
}
