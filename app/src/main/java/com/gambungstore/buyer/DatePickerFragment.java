package com.gambungstore.buyer;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TheListener listener;

    public interface TheListener {
        public void returnDate(String date);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());

        if (listener != null) {
            listener.returnDate(formattedDate);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year, month, day;
        listener = (TheListener) getActivity();

        Bundle bundle = getArguments();
        String dateString = bundle.getString("dateAsText");
        String[] dateParts = dateString.split("-");

        if (dateString.matches("")) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            day = Integer.parseInt(dateParts[2]);
            month = Integer.parseInt(dateParts[1]) - 1;
            year = Integer.parseInt(dateParts[0]);
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
}
