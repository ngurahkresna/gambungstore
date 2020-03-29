package com.example.gambungstore.models.checkout;

import com.example.gambungstore.models.Profile;
import com.example.gambungstore.models.store.DataStore;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Checkout {

    @SerializedName("user")
    private Profile user;
    @SerializedName("store")
    private List<DataStore> store;
    @SerializedName("total_price")
    private int price;
    @SerializedName("weight")
    private List<Weight> weights;

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public List<DataStore> getStore() {
        return store;
    }

    public void setStore(List<DataStore> store) {
        this.store = store;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Weight> getWeights() {
        return weights;
    }

    public void setWeights(List<Weight> weights) {
        this.weights = weights;
    }
}
