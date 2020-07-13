package com.example.gambungstore.models;

import com.example.gambungstore.models.product.DataProduct;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bank {
    //
    @SerializedName("bank")
    @Expose
    private Banks banks;

    public Banks getBanks() {
        return banks;
    }

    public void setBanks(Banks banks) {
        this.banks = banks;
    }
}
