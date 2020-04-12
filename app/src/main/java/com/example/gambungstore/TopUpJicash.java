package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class TopUpJicash extends AppCompatActivity {
    Button topUpbtn;
    ImageView backButton;
    EditText mAmmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_jicash);

        mAmmount = findViewById(R.id.etAmount);

        backButton = findViewById(R.id.backButtonJiCash);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        topUpbtn = findViewById(R.id.btnTopUp);
        topUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopUpJicash.this, JicashCheckoutForm.class);
                int ammount = Integer.valueOf(mAmmount.getText().toString());
                intent.putExtra("ammount", ammount);
                startActivity(intent);
            }
        });
    }
}
