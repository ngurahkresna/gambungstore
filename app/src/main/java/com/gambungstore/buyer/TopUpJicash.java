package com.gambungstore.buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class TopUpJicash extends AppCompatActivity {
    static TopUpJicash topUpJicash;
    Button topUpbtn;
    ImageView backButton;
    EditText mAmmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up_jicash);
        topUpJicash = this;

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
                if (mAmmount.getText().toString().equals("")){
                    Toast.makeText(TopUpJicash.this, "Jumlah Top-Up tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    int ammount = Integer.valueOf(mAmmount.getText().toString());
                    if (ammount < 10000){
                        Toast.makeText(TopUpJicash.this, "Jumlah Top-up minimal Rp. 10.000", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.putExtra("ammount", ammount);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    public static TopUpJicash getInstance(){
        return topUpJicash;
    }
}
