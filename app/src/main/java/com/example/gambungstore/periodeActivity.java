package com.example.gambungstore;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class periodeActivity extends AppCompatActivity implements DatePickerFragment.TheListener{

    EditText fromDate, untilDate;
    Button applyButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periode);
        fromDate = findViewById(R.id.datePickerFrom);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        // Masih terduplikat jika di apply tanggal nya
        untilDate = findViewById(R.id.untilDateEditText);
        untilDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);
            }
        });

        applyButton = findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(periodeActivity.this, "Berhasil diterapkan!", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    public void showDatePicker(View view) {
        DialogFragment datePicker = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dateAsText", fromDate.getText().toString());
        datePicker.setArguments(bundle);
        datePicker.show(getSupportFragmentManager(),"datePicker");
    }
    @Override
    public void returnDate(String date) {
        fromDate.setText(date);
        untilDate.setText(date);
    }


}
