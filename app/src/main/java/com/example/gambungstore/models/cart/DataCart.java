package com.example.gambungstore.models.cart;

import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.product.Product;
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
