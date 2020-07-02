package com.example.gambungstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MasukNominal extends AppCompatActivity {

    TextView nominal;
    int nomin = 0;
    RadioGroup radioGroup;
    BottomSheetDialog dialog;
    View newView;
    Boolean useJicash = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk_nominal);
        nominal = findViewById(R.id.TextNominal);
        newView = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_transaksi, null);
        dialog = new BottomSheetDialog(this);
        dialog.setContentView(newView);

        radioGroup = newView.findViewById(R.id.radioButtonGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case(R.id.butTr):
                        useJicash = false;
                        break;
                    case(R.id.butJi):
                        useJicash = true;
                        break;
                }
            }
        });
    }

    public void beriDonasi(View view) {

        if(nomin >= 10000 && !nominal.getText().equals(""))
        {
            dialog.show();
        }
        else if (nomin>=10000){
            Toast.makeText(MasukNominal.this,"Nominal kurang dari minimal donasi",Toast.LENGTH_SHORT).show();
        }
        else if(nominal.getText().equals("")){
            Toast.makeText(MasukNominal.this,"Masukkan nominal terlebih dahulu",Toast.LENGTH_SHORT).show();
        }
    }

    public void isiNominal(View view){
        switch(view.getId()){
            case(R.id.sepribu):
                nominal.setText("10.000");
                nomin = 10000;
                break;
            case(R.id.duapribu):
                nominal.setText("20.000");
                nomin = 20000;
                break;
            case(R.id.limapribu):
                nominal.setText("50.000");
                nomin = 50000;
                break;
            case(R.id.serribu):
                nominal.setText("100.000");
                nomin = 100000;
                break;
            case(R.id.duarribu):
                nominal.setText("200.000");
                nomin = 200000;
                break;
            case(R.id.limarribu):
                nominal.setText("500.000");
                nomin = 500000;
                break;
        }
    }

    public void konfirmNominal(View view)
    {
        if(useJicash)
        {
            Toast.makeText(MasukNominal.this,"Fitur belum tersedia",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this, TransferDonasi.class);
            startActivity(intent);
        }
    }

    public void detailDonasi(View view) {
        Intent intent = new Intent(this, DetailDonasi.class);
        startActivity(intent);
    }
}
