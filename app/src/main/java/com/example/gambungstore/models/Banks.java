package com.example.gambungstore.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Banks {
    @SerializedName("data")
    @Expose
    private List<ResultBank> resultBanks;

    public List<ResultBank> getResultBanks() {
        return resultBanks;
    }

    public void setResultBanks(List<ResultBank> resultBanks) {
        this.resultBanks = resultBanks;
    }
}
