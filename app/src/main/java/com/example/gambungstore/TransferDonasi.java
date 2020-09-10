package com.example.gambungstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gambungstore.client.Client;
import com.example.gambungstore.models.Bank;
import com.example.gambungstore.models.Profile;
import com.example.gambungstore.services.Services;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferDonasi extends AppCompatActivity {
    Button buttonUpload;
    Button buttonFile;
    EditText textFile;
    int nominal;
    Spinner dropdownBank;
    ArrayList<String> daftarBank;
    ArrayList<Integer> kodeBank;
    int kodeBankTerpilih = 0;
    boolean isBankSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_donasi);
        Intent intent = getIntent();
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonFile = findViewById(R.id.buttonfile);
        textFile = findViewById(R.id.textfile);
        dropdownBank = findViewById(R.id.spinnerBank);
        nominal = intent.getIntExtra("nominal",0);
        daftarBank = new ArrayList<>();
        kodeBank = new ArrayList<>();

        Services service = Client.getClient(Client.BASE_URL).create(Services.class);
        Call<List<Bank>> banks = service.getAllBanks();
        banks.enqueue(new Callback<List<Bank>>() {
            @Override
            public void onResponse(Call<List<Bank>> call, Response<List<Bank>> response) {
                Log.d("Koneksi Database Bank", "onResponse: "+response.raw());
                for(Bank rs : response.body()){
                    daftarBank.add(rs.getBank_name());
                    kodeBank.add(rs.getBank_code());
                }
            }

            @Override
            public void onFailure(Call<List<Bank>> call, Throwable t) {
                Log.d("Koneksi Database Bank", "onFailure: " + t);
            }
        });

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, daftarBank);
        dropdownBank.setAdapter(adapterSpinner);
        dropdownBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kodeBankTerpilih = kodeBank.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isBankSelected = false;
            }
        });
    }
}
