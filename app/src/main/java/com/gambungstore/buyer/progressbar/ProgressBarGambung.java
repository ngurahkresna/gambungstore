package com.gambungstore.buyer.progressbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;

import com.gambungstore.buyer.R;

public class ProgressBarGambung  {
    private static final String TAG = "ProgressBarGambung";

    Activity activity;
    AlertDialog alertDialog;

    public ProgressBarGambung(Activity activity) {
        this.activity = activity;
    }

    public void startProgressBarGambung(){

        Log.d(TAG, "startProgressBarGambung: "+activity);
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);

        LayoutInflater inflater = this.activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progress_bar, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void endProgressBarGambung(){
        alertDialog.dismiss();
    }
}
