package com.example.gambungstore.models.cart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart {

    @SerializedName("data")
    private List<DataCart> dataCart;

    public List<DataCart> getDataCart() {
        return dataCart;
    }

    public void setDataCart(List<DataCart> dataCart) {
        this.dataCart = dataCart;
    }
}
