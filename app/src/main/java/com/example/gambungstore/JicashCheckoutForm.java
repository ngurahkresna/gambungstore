package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class JicashCheckoutForm extends AppCompatActivity {
    TextView backToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jicash_checkout_form);
        backToHome = findViewById(R.id.backToHome);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(JicashCheckoutForm.this, homeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void bayarJicash(View view) {
        Intent intent = new Intent(JicashCheckoutForm.this, jicashAlert.class);
        startActivity(intent);
    }
}
