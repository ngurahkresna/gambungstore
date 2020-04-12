package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.gambungstore.R;

public class jiCashHomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView rvHistory;
    LinearLayoutManager linearLayoutManager;
    ImageView backView;
    Button btnTopup, btnPeriode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_cash_home);
        Spinner spinner = findViewById(R.id.spinnerbar);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaksi_jicash, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }
        btnTopup = findViewById(R.id.topupjicash);
        btnTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jiCashHomeActivity.this, TopUpJicash.class);
                startActivity(intent);
            }
        });
        backView = findViewById(R.id.backButtonJiCash);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnPeriode = findViewById(R.id.periodeButton);
        btnPeriode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(jiCashHomeActivity.this, periodeActivity.class);
                startActivity(intent);
            }
        });
        historyCardAdapter();
    }

    public void historyCardAdapter(){
        rvHistory = findViewById(R.id.jicashHistoyRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        rvHistory.setLayoutManager(linearLayoutManager);
        jicashHistoryCardAdapter jicashHistoryCardAdapter = new jicashHistoryCardAdapter();
        rvHistory.setAdapter(jicashHistoryCardAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#388A6B"));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.transaksi_jicash, android.R.layout.simple_spinner_item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
