package com.gambungstore.buyer.models.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("data")
    private List<DataProduct> products;

    public List<DataProduct> getProducts() {
        return products;
    }

    public void setProducts(List<DataProduct> products) {
        this.products = products;
    }
}
