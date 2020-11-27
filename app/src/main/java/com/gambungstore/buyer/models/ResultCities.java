package com.gambungstore.buyer.models;

import com.google.gson.annotations.SerializedName;

public class ResultCities {
    @SerializedName("city_id")
    private int city_id;
    @SerializedName("city_name")
    private String city_name;

    public int getCityId() {
        return city_id;
    }

    public void setCityId(int city_id) {
        this.city_id = city_id;
    }

    public String getCityName() {
        return city_name;
    }

    public void setCityName(String city_name) {
        this.city_name = city_name;
    }
}
