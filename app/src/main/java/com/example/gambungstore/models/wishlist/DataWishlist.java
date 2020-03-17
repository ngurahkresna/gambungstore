package com.example.gambungstore.models.wishlist;

import com.example.gambungstore.models.product.DataProduct;
import com.example.gambungstore.models.product.Product;
import com.google.gson.annotations.SerializedName;

public class DataWishlist {

    @SerializedName("id")
    private int id;
    @SerializedName("id_users")
    private int id_users;
    @SerializedName("products")
    private DataProduct product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public DataProduct getProduct() {
        return product;
    }

    public void setProduct(DataProduct product) {
        this.product = product;
    }
}
