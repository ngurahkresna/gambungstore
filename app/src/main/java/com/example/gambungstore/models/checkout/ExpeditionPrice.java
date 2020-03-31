package com.example.gambungstore.models.checkout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpeditionPrice {

    @SerializedName("service")
    private String service;
    @SerializedName("description")
    private String description;
    @SerializedName("cost")
    private List<ExpeditionCost> cost;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExpeditionCost> getCost() {
        return cost;
    }

    public void setCost(List<ExpeditionCost> cost) {
        this.cost = cost;
    }
}
