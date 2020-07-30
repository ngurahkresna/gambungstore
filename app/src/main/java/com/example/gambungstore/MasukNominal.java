package com.example.gambungstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.Locale;

public class MasukNominal extends AppCompatActivity {

    EditText nominal;
    int nomin = 0;
    RadioGroup radioGroup;
    BottomSheetDialog dialog;
    View newView;
    int paymentMethod = 1;
    String nominRupiah = "";
    NumberFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_nominal);

        nominal = findViewById(R.id.TextNominal);
        newView = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_transaksi, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(newView);
        format = NumberFormat.getCurrencyInstance(new Locale("in","ID"));

        radioGroup = newView.findViewById(R.id.radioButtonGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case(R.id.butTr):
                        paymentMethod = 2;
                        break;
                    case(R.id.butJi):
                        paymentMethod = 1;
                        break;
                }
            }
        });
    }

    public void beriDonasi(View view) {
        try {
            nomin = Integer.parseInt(nominal.getText().toString());
        }catch(Exception e){
            Log.d("nominal",e.getMessage());
        }

        if(nomin >= 10000 && nominal.getText().length() <= 3)
        {
            dialog.show();
            nominRupiah = format.format(nomin);
        }
        else if (nomin <= 10000){
            Toast.makeText(MasukNominal.this,"Nominal kurang dari minimal donasi",Toast.LENGTH_SHORT).show();
        }
        else if(nominal.getText().length() <= 3){
            Toast.makeText(MasukNominal.this,"Masukkan nominal terlebih dahulu",Toast.LENGTH_SHORT).show();
        }
    }

    public void isiNominal(View view){
        switch(view.getId()){
            case(R.id.sepribu):
                nominal.setText("10000");
                nomin = 10000;
                break;
            case(R.id.duapribu):
                nominal.setText("20000");
                nomin = 20000;
                break;
            case(R.id.limapribu):
                nominal.setText("50000");
                nomin = 50000;
                break;
            case(R.id.serribu):
                nominal.setText("100000");
                nomin = 100000;
                break;
            case(R.id.duarribu):
                nominal.setText("200000");
                nomin = 200000;
                break;
            case(R.id.limarribu):
                nominal.setText("500000");
                nomin = 500000;
                break;
        }
    }

    public void konfirmNominal(View view)
    {
        if(paymentMethod == 1)
        {
            Toast.makeText(MasukNominal.this,"Fitur belum tersedia",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this, TransferDonasi.class);
            intent.putExtra("nominalpembayaran",nomin);
            intent.putExtra("nominaldalamrp",nominRupiah);
            startActivity(intent);
        }
    }

    public void detailDonasi(View view) {
        Intent intent = new Intent(this, DetailDonasi.class);
        startActivity(intent);
    }
}
