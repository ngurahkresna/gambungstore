package com.example.gambungstore.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RajaOngkir {

    @SerializedName("rajaongkir")
    @Expose
    private Cities cities;

    public Cities getCities() {
        return cities;
    }

    public void setCities(Cities cities) {
        this.cities = cities;
    }
}
