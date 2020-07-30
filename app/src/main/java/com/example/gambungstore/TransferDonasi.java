package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class TransferDonasi extends AppCompatActivity {
    Button buttonUpload;
    Button buttonFile;
    EditText textFile;
    int nominal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_donasi);
        Intent intent = getIntent();
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonFile = findViewById(R.id.buttonfile);
        textFile = findViewById(R.id.textfile);

        nominal = intent.getIntExtra("nominal",0);
    }
}
