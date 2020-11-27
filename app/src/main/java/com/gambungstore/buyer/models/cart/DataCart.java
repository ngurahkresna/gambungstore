package com.gambungstore.buyer.models.cart;

import com.gambungstore.buyer.models.product.DataProduct;
import com.google.gson.annotations.SerializedName;

public class DataCart {

    @SerializedName("id")
    private int id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("price")
    private int price;
    @SerializedName("product")
    private DataProduct product;
    @SerializedName("username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DataProduct getProduct() {
        return product;
    }

    public void setProduct(DataProduct product) {
        this.product = product;
    }
}
