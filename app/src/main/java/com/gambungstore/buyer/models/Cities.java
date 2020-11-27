package com.gambungstore.buyer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cities {

    @SerializedName("results")
    @Expose
    private List<ResultCities> result;
    @SerializedName("status")
    @Expose
    private StatusOngkir status;

    public StatusOngkir getStatus() {
        return status;
    }

    public void setStatus(StatusOngkir status) {
        this.status = status;
    }

    public List<ResultCities> getResult() {
        return result;
    }

    public void setResult(List<ResultCities> result) {
        this.result = result;
    }
}
