package com.example.gambungstore.models.checkout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Expedition {

    @SerializedName("id")
    private int id;
    @SerializedName("expedition_code")
    private String expedition_code;
    @SerializedName("store_code")
    private String store_code;
    @SerializedName("expedition_name")
    private String expedition_name;
    @SerializedName("price")
    private List<ExpeditionPrice> price;

    public String getExpedition_name() {
        return expedition_name;
    }

    public void setExpedition_name(String expedition_name) {
        this.expedition_name = expedition_name;
    }

    public List<ExpeditionPrice> getPrice() {
        return price;
    }

    public void setPrice(List<ExpeditionPrice> price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpedition_code() {
        return expedition_code;
    }

    public void setExpedition_code(String expedition_code) {
        this.expedition_code = expedition_code;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }
}
