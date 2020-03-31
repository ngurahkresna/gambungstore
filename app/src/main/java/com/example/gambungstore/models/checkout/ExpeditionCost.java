package com.example.gambungstore.models.checkout;

import com.google.gson.annotations.SerializedName;

public class ExpeditionCost {

    @SerializedName("value")
    private int value;
    @SerializedName("etd")
    private String day;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
