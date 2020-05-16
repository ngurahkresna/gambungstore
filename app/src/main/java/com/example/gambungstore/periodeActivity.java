package com.example.gambungstore;

import android.content.Intent;
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

    boolean isFrom, isUntil;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periode);

        fromDate = findViewById(R.id.datePickerFrom);
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFrom = true;
                isUntil = false;
                showDatePicker(v);
            }
        });

        untilDate = findViewById(R.id.untilDateEditText);
        untilDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFrom = false;
                isUntil = true;
                showDatePicker(v);
            }
        });

        applyButton = findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(periodeActivity.this, "Berhasil diterapkan!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("from_date", fromDate.getText().toString());
                intent.putExtra("until_date", untilDate.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
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
        if (isFrom)
            fromDate.setText(date);
        if (isUntil)
            untilDate.setText(date);
    }


}
